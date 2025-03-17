package com.yunhan.presentation.detail.component

data class DetailState(
    val text: String = "",
)

sealed interface DetailSideEffect {

}

sealed interface DetailAction {

}