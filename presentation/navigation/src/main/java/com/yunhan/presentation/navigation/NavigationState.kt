package com.yunhan.presentation.navigation

data class NavigationState(
    val sampleNavType: SampleNavType = SampleNavType.TEST1,
)

sealed interface NavigationSideEffect {
    data class StartDetailActivity(val sampleNavType: SampleNavType) : NavigationSideEffect
}

sealed interface NavigationAction {
    data class StartDetailActivity(val sampleNavType: SampleNavType) : NavigationAction
}