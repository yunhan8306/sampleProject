package com.yunhan.presentation.base

interface BaseState {
    val status: BaseStatus
    fun loading(): BaseState
    fun success(): BaseState
    fun error(): BaseState
}



abstract class BaseStateImpl<T : BaseState>(
    override val status: BaseStatus = BaseStatus.Idle
) : BaseState {
    abstract fun copyState(status: BaseStatus): T
    override fun loading(): T = copyState(BaseStatus.Loading)
    override fun success(): T = copyState(BaseStatus.Success)
    override fun error(): T = copyState(BaseStatus.Error)
}

sealed interface BaseStatus {
    data object Idle : BaseStatus
    data object Loading : BaseStatus
    data object Success : BaseStatus
    data object Error : BaseStatus
}

interface BaseSideEffect {

}

interface BaseAction {

}
