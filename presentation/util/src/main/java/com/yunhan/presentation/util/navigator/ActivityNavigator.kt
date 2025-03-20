package com.yunhan.presentation.util.navigator

import android.content.Context
import android.content.Intent

interface ActivityNavigator {
    fun navigateTo(context: Context, type: ActivityNavigatorType): Intent
}

enum class ActivityNavigatorType {
    DETAIL
}