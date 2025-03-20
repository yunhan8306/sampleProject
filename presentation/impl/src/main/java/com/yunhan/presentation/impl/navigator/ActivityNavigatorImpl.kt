package com.yunhan.presentation.impl.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.yunhan.presentation.detail.DetailActivity
import com.yunhan.presentation.util.navigator.ActivityNavigator
import com.yunhan.presentation.util.navigator.ActivityNavigatorType
import javax.inject.Inject

class ActivityNavigatorImpl @Inject constructor(

): ActivityNavigator {
    private fun getActivity(type: ActivityNavigatorType): Class<out Activity> {
        return when(type) {
            ActivityNavigatorType.DETAIL -> DetailActivity::class.java
        }
    }

    override fun navigateTo(context: Context, type: ActivityNavigatorType): Intent {
        return Intent(context, getActivity(type))
    }
}