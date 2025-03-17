package com.yunhan.presentation.navigation

import com.yunhan.presentation.base.BaseAction
import com.yunhan.presentation.base.BaseSideEffect
import com.yunhan.presentation.base.BaseStateImpl
import com.yunhan.presentation.base.BaseStatus

data class NavigationState(
    val sampleNavType: SampleNavType = SampleNavType.TEST1,
    override val status: BaseStatus = BaseStatus.Idle,
): BaseStateImpl<NavigationState>() {
    companion object {
        val init = NavigationState()
    }

    override fun copyState(status: BaseStatus): NavigationState {
        return copy(status = status)
    }
}

sealed interface NavigationSideEffect: BaseSideEffect {
    data class StartDetailActivity(val sampleNavType: SampleNavType) : NavigationSideEffect
}

sealed interface NavigationAction: BaseAction {
    data class StartDetailActivity(val sampleNavType: SampleNavType) : NavigationAction
}