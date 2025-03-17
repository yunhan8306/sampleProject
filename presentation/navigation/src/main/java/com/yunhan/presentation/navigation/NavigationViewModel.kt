package com.yunhan.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.yunhan.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            else -> Unit
        }
    }

    private fun startDetailActivity(sampleNavType: SampleNavType) {
        viewModelScope.launch {
            postSideEffect(NavigationSideEffect.StartDetailActivity(sampleNavType))
        }
    }

    init {
        viewModelScope.fetch(
            onInit = {
                delay(1000)
            },
            onLoading = {
                delay(1000)
            },
            onSuccess = {
                reduce(currentState.copy(sampleNavType = sampleNavType))
            }
        )
    }
}