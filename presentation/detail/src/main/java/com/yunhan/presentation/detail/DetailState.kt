package com.yunhan.presentation.detail

import com.yunhan.presentation.base.BaseAction
import com.yunhan.presentation.base.BaseSideEffect
import com.yunhan.presentation.base.BaseStateImpl
import com.yunhan.presentation.base.BaseStatus

data class DetailState(
    val text: String = "",
    override val status: BaseStatus = BaseStatus.Idle,
): BaseStateImpl<DetailState>() {
    companion object {
        val init = DetailState()
    }

    override fun copyState(status: BaseStatus): DetailState {
        return copy(status = status)
    }
}

sealed interface DetailSideEffect: BaseSideEffect {

}

sealed interface DetailAction: BaseAction {

}