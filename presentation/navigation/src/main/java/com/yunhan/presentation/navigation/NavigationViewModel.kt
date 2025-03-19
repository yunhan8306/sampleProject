package com.yunhan.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.yunhan.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(

) : BaseViewModel<NavigationState, NavigationSideEffect, NavigationAction>(NavigationState.init) {

    private var _sampleNavType: SampleNavType = SampleNavType.TEST1
    private val sampleNavType: SampleNavType get() = _sampleNavType

    fun setSampleNav(sampleNavType: SampleNavType) {
        _sampleNavType = sampleNavType
    }

    override fun onAction(action: NavigationAction) {
        when(action) {
            is NavigationAction.StartDetailActivity -> {
                startDetailActivity(action.sampleNavType)
            }
            is NavigationAction.CancelLoading -> {
                cancelJobList()
            }
            else -> Unit
        }
    }

    private fun startDetailActivity(sampleNavType: SampleNavType) {
        viewModelScope.intent(
            isShowLoading = true
        ) {
            delay(3000)
            postSideEffect(NavigationSideEffect.StartDetailActivity(sampleNavType))
        }
    }

    init {
        viewModelScope.fetch(
            onStart = {
                delay(1000)
            },
            onRendering = {
                delay(1000)
            },
            onComplete = {
                reduce(currentState.copy(sampleNavType = sampleNavType))
            }
        )
    }
}