package com.yunhan.presentation.detail

import com.yunhan.presentation.base.BaseAction
import com.yunhan.presentation.base.BaseSideEffect
import com.yunhan.presentation.base.AbstractBaseState
import com.yunhan.presentation.base.BaseStatus

data class DetailState(
    override val status: BaseStatus,
    override val isLoading: Boolean,
    val text: String = "",
): AbstractBaseState<DetailState>() {
    companion object {
        val init = DetailState(
            status = BaseStatus.Idle,
            isLoading = false,
            text = ""
        )
    }

    override fun copyAndUpdateStatus(status: BaseStatus): DetailState {
        return copy(status = status)
    }

    override fun copyAndUpdateLoading(isLoading: Boolean): DetailState {
        return copy(isLoading = isLoading)
    }
}

sealed interface DetailSideEffect: BaseSideEffect {

}

sealed interface DetailAction: BaseAction {

}