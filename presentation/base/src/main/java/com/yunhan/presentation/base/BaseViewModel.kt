package com.yunhan.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val jobList = CopyOnWriteArrayList<Job>()

    open fun initFetch() {}

    fun reduce(state: STATE.() -> STATE) {
        _state.update(state)
    }

    suspend fun postSideEffect(sideEffect: SIDE_EFFECT) {
        _sideEffect.emit(sideEffect)
    }

    abstract fun onAction(action: ACTION)

    fun CoroutineScope.intent(
        isShowLoading: Boolean = false,
        onError: ((Throwable) -> Unit)? = null,
        onAction: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            context = CoroutineExceptionHandler { _, throwable ->
                onError?.invoke(throwable)
            }
        ) {
            onAction()
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
        onError: ((Throwable) -> Unit)? = null, // 세팅 오류 처리
        onAction: suspend CoroutineScope.() -> Unit, // api 호출, 로컬 데이터 세팅 등 초기 작업,
        onCompleted: (() -> Unit)? = null, // 세팅 후 처리
    ) {
        launch(
            context = CoroutineExceptionHandler { _, throwable ->
                reduce { error() as STATE }
                onError?.invoke(throwable)
            }
        ) {
            reduce { rendering() as STATE }
            onAction()
        }.invokeOnCompletion { throwable ->
            if(throwable != null) {
                reduce { error() as STATE }
                onError?.invoke(throwable)
            } else {
                reduce { complete() as STATE }
                onCompleted?.invoke()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun MutableList<Job>.offerJob(
        job: Job
    ) {
        viewModelScope.launch {
            if(!contains(job)) {
                add(job)
            }
            delay(1000)
            this@BaseViewModel.reduce {
                if(isNotEmpty()) showLoading() as STATE else hideLoading() as STATE
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun MutableList<Job>.removeJob(
        job: Job
    ) {
        if(contains(job)) {
            remove(job)
        }
        this@BaseViewModel.reduce {
            if(isNotEmpty()) showLoading() as STATE else hideLoading() as STATE
        }
    }

    fun cancelJobList() {
        if(jobList.isEmpty()) return
        jobList.forEach { it.cancel() }
        jobList.clear()
    }
}