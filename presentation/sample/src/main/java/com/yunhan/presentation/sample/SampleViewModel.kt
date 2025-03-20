package com.yunhan.presentation.sample

import androidx.lifecycle.viewModelScope
import com.yunhan.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(

) : BaseViewModel<SampleState, SampleSideEffect, SampleAction>(SampleState.init) {

    private var _sampleNavType: SampleNavType = SampleNavType.TEST1
    private val sampleNavType: SampleNavType get() = _sampleNavType

    fun setSampleNav(sampleNavType: SampleNavType) {
        _sampleNavType = sampleNavType
    }

    override fun onAction(action: SampleAction) {
        when(action) {
            is SampleAction.StartDetailActivity -> {
                startDetailActivity(action.sampleNavType)
            }
            is SampleAction.CancelLoading -> {
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
            postSideEffect(SampleSideEffect.StartDetailActivity(sampleNavType))
        }
    }

    init {
        viewModelScope.fetch(
            onStart = {
                delay(3000)
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