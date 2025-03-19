package com.yunhan.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<STATE: BaseState, SIDE_EFFECT: BaseSideEffect, ACTION: BaseAction>(
    initState: STATE
) : ViewModel() {

    private val _state = MutableStateFlow(initState)
    val state = _state.asStateFlow()

    val currentState: STATE get() = state.value

    private val _sideEffect = MutableSharedFlow<SIDE_EFFECT>(extraBufferCapacity = 10)
    val sideEffect = _sideEffect.asSharedFlow()

    private val _isShowLoading = MutableStateFlow(false)
    val isShowLoading = _isShowLoading.asStateFlow()

    private val jobList = CopyOnWriteArrayList<Job>()

    suspend fun reduce(state: STATE) {
        _state.emit(state)
    }

    suspend fun postSideEffect(sideEffect: SIDE_EFFECT) {
        _sideEffect.emit(sideEffect)
    }

    abstract fun onAction(action: ACTION)

    fun CoroutineScope.intent(
        isShowLoading: Boolean = false,
        onError: (Throwable) -> Unit = {},
        onAction: suspend () -> Unit
    ) {
        launch(
            context = EmptyCoroutineContext + CoroutineExceptionHandler { _, throwable ->
                if(throwable is CancellationException) return@CoroutineExceptionHandler
                onError.invoke(throwable)
            }
        ) {
            onAction.invoke()
        }.let { job ->
            if(isShowLoading) {
                jobList.offerJob(job)
            }
            job.invokeOnCompletion {
                jobList.removeJob(job)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun CoroutineScope.fetch(
        onStart: suspend () -> Unit = {}, // api 호출, 로컬 데이터 세팅 등 초기 작업,
        onRendering: suspend () -> Unit = {}, // 스켈레톤 등 렌더링 될 때 필요한 처리
        onComplete: suspend () -> Unit = {}, // 세팅 후 처리
        onError: (Throwable) -> Unit = {} // 세팅 오류 처리
    ) {
        launch(
            context = EmptyCoroutineContext + CoroutineExceptionHandler { _, throwable ->
                onError.invoke(throwable)
            }
        ) {
            launch { onStart.invoke() }
            coroutineScope {
                reduce(currentState.rendering() as STATE)
                onRendering.invoke()
            }
            coroutineScope {
                reduce(_state.value.complete() as STATE)
                onComplete.invoke()
            }
        }
    }

    private fun MutableList<Job>.offerJob(
        job: Job
    ) {
        viewModelScope.launch {
            if(!contains(job)) {
                add(job)
            }
            delay(1000)
            _isShowLoading.tryEmit(isNotEmpty())
        }
    }

    private fun MutableList<Job>.removeJob(
        job: Job
    ) {
        if(contains(job)) {
            remove(job)
        }
        _isShowLoading.tryEmit(isNotEmpty())
    }

    fun cancelJobList() {
        if(jobList.isEmpty()) return
        jobList.forEach { it.cancel() }
        jobList.clear()
    }
}