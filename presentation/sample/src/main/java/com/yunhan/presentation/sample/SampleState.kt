package com.yunhan.presentation.sample

import android.content.Context
import android.content.Intent
import com.yunhan.presentation.base.BaseAction
import com.yunhan.presentation.base.BaseSideEffect
import com.yunhan.presentation.base.BaseStateImpl
import com.yunhan.presentation.base.BaseStatus

data class SampleState(
    val sampleNavType: SampleNavType = SampleNavType.TEST1,
    val cnt: Int = 0,
    override val status: BaseStatus = BaseStatus.Idle,
): BaseStateImpl<SampleState>() {
    companion object {
        val init = SampleState()
    }

    override fun copyState(status: BaseStatus): SampleState {
        return copy(status = status)
    }
}

sealed interface SampleSideEffect: BaseSideEffect {
    data class StartDetailActivity(val intent: Intent) : SampleSideEffect
}

sealed interface SampleAction: BaseAction {
    data object CancelLoading : SampleAction
    data object SendUp : SampleAction
    data class StartDetailActivity(val context: Context, val sampleNavType: SampleNavType) : SampleAction
}