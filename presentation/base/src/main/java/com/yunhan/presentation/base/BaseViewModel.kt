package com.yunhan.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
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

    private val jobList = mutableListOf<Job>()

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
        onInit: suspend () -> Unit = {}, // api 호출, 로컬 데이터 세팅 등 초기 작업,
        onLoading: suspend () -> Unit = {}, // 스켈레톤 등 로딩 처리
        onSuccess: suspend () -> Unit = {}, // 세팅 후 처리
        onError: (Throwable) -> Unit = {} // 세팅 오류 처리
    ) {
        launch(
            context = EmptyCoroutineContext + CoroutineExceptionHandler { _, throwable ->
                onError.invoke(throwable)
            }
        ) {
            onInit.invoke()
            coroutineScope {
                reduce(currentState.loading() as STATE)
                onLoading.invoke()
            }
            coroutineScope {
                reduce(_state.value.success() as STATE)
                onSuccess.invoke()
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
        jobList.forEach { it.cancel() }
        jobList.clear()
    }
}