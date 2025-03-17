package com.yunhan.presentation.navigation.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.yunhan.presentation.navigation.SampleNavType

@Composable
fun NavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = SampleNavType.TEST1.name
    ) {
        sampleScreen(SampleNavType.TEST1)
        sampleScreen(SampleNavType.TEST2)
        sampleScreen(SampleNavType.TEST3)
        sampleScreen(SampleNavType.TEST4)
    }
}