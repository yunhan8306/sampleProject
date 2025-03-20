package com.yunhan.presentation.base

interface BaseState {
    val status: BaseStatus
    fun rendering(): BaseState
    fun complete(): BaseState
    fun error(): BaseState
}

abstract class BaseStateImpl<T : BaseState>(
    override val status: BaseStatus = BaseStatus.Idle
) : BaseState {
    abstract fun copyState(status: BaseStatus): T
    override fun rendering(): T = copyState(BaseStatus.Rendering)
    override fun complete(): T = copyState(BaseStatus.Complete)
    override fun error(): T = copyState(BaseStatus.Error)
}

sealed interface BaseStatus {
    data object Idle : BaseStatus
    data object Rendering : BaseStatus
    data object Complete : BaseStatus
    data object Error : BaseStatus
}

interface BaseSideEffect

interface BaseAction
