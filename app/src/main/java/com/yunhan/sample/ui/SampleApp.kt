package com.yunhan.sample.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yunhan.presentation.navigation.SampleNavHost
import com.yunhan.presentation.navigation.SampleNavigation

@Composable
fun SampleApp(

) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            SampleNavigation(navHostController)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SampleNavHost(
                navHostController = navHostController,
            )
        }
    }
}