package com.yunhan.presentation.sample

import android.content.Context
import android.content.Intent
import com.yunhan.presentation.base.BaseAction
import com.yunhan.presentation.base.BaseSideEffect
import com.yunhan.presentation.base.AbstractBaseState
import com.yunhan.presentation.base.BaseStatus
import com.yunhan.presentation.detail.DetailState

data class SampleState(
    override val status: BaseStatus,
    override val isLoading: Boolean,
    val sampleNavType: SampleNavType,
    val cnt: Int
): AbstractBaseState<SampleState>() {
    companion object {
        val init = SampleState(
            status = BaseStatus.Idle,
            isLoading = false,
            sampleNavType = SampleNavType.TEST1,
            cnt = 0
        )
    }

    override fun copyAndUpdateStatus(status: BaseStatus): SampleState {
        return copy(status = status)
    }

    override fun copyAndUpdateLoading(isLoading: Boolean): SampleState {
        return copy(isLoading = isLoading)
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