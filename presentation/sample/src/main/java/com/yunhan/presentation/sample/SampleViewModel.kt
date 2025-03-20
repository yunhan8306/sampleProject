package com.yunhan.presentation.sample

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.yunhan.presentation.base.BaseViewModel
import com.yunhan.presentation.util.navigator.ActivityNavigator
import com.yunhan.presentation.util.navigator.ActivityNavigatorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val activityNavigator: ActivityNavigator
) : BaseViewModel<SampleState, SampleSideEffect, SampleAction>(SampleState.init) {

    private var _sampleNavType: SampleNavType = SampleNavType.TEST1
    private val sampleNavType: SampleNavType get() = _sampleNavType

    fun setSampleNav(sampleNavType: SampleNavType) {
        _sampleNavType = sampleNavType
    }

    override fun onAction(action: SampleAction) {
        when(action) {
            is SampleAction.StartDetailActivity -> {
                startDetailActivity(action.context, action.sampleNavType)
            }
            is SampleAction.CancelLoading -> {
                cancelJobList()
            }
            else -> Unit
        }
    }

    private fun startDetailActivity(context: Context, sampleNavType: SampleNavType) {
        viewModelScope.intent(
            isShowLoading = true
        ) {
            delay(3000)
            activityNavigator.navigateTo(context, ActivityNavigatorType.DETAIL)
                .putExtra("from", sampleNavType.name)
                .let { postSideEffect(SampleSideEffect.StartDetailActivity(it)) }
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