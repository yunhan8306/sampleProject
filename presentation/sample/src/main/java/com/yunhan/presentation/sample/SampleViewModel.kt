package com.yunhan.presentation.sample

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
import com.yunhan.domain.sample.usecase.SendUpUseCase
import com.yunhan.presentation.base.BaseViewModel
import com.yunhan.presentation.util.navigator.ActivityNavigator
import com.yunhan.presentation.util.navigator.ActivityNavigatorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sendUpUseCase: SendUpUseCase,
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
            is SampleAction.SendUp -> {
                sendUp()
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

    private fun sendUp() {
        viewModelScope.intent {
            val cnt = sendUpUseCase.invoke().cnt
            reduce { copy(cnt = cnt) }
        }
    }

    override fun initFetch() {
        viewModelScope.fetch(
            onAction = {
                delay(3000)
            },
            onCompleted = {
                reduce { copy(sampleNavType = sampleNavType) }
            }
        )
    }

    init {
        initFetch()
    }
}