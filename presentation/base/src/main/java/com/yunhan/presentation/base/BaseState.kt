package com.yunhan.presentation.base

interface BaseState {
    val status: BaseStatus
    fun rendering(): BaseState
    fun complete(): BaseState
    fun error(): BaseState

    val isLoading: Boolean
    fun showLoading(): BaseState
    fun hideLoading(): BaseState
}

abstract class AbstractBaseState<T : BaseState>(
    override val status: BaseStatus = BaseStatus.Idle
) : BaseState {
    abstract fun copyAndUpdateStatus(status: BaseStatus): T
    override fun rendering(): T = copyAndUpdateStatus(BaseStatus.Rendering)
    override fun complete(): T = copyAndUpdateStatus(BaseStatus.Complete)
    override fun error(): T = copyAndUpdateStatus(BaseStatus.Error)

    abstract fun copyAndUpdateLoading(isLoading: Boolean): T
    override fun showLoading(): T = copyAndUpdateLoading(true)
    override fun hideLoading(): T = copyAndUpdateLoading(false)
}

sealed interface BaseStatus {
    data object Idle : BaseStatus
    data object Rendering : BaseStatus
    data object Complete : BaseStatus
    data object Error : BaseStatus
}

interface BaseSideEffect

interface BaseAction
