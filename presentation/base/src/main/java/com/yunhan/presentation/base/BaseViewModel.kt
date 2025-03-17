package com.yunhan.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
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

    suspend fun reduce(state: STATE) {
        _state.emit(state)
    }

    suspend fun postSideEffect(sideEffect: SIDE_EFFECT) {
        _sideEffect.emit(sideEffect)
    }

    abstract fun onAction(action: ACTION)

    @Suppress("UNCHECKED_CAST")
    fun CoroutineScope.fetch(
        onInit: suspend () -> Unit = {}, // api 호출, 로컬 데이터 세팅 등 초기 작업,
        onLoading: suspend () -> Unit = {}, // 스켈레톤 등 로딩 처리
        onSuccess: suspend () -> Unit = {}, // 세팅 후 처리
        onError: () -> Unit = {}, // 세팅 오류 처리
    ) {
        launch(
            context = EmptyCoroutineContext + CoroutineExceptionHandler { _, throwable ->
                when(throwable) {
                    else -> Unit
                }
                onError.invoke()
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
}