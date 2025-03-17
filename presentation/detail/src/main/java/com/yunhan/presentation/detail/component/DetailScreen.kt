package com.yunhan.presentation.detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunhan.presentation.detail.DetailState
import com.yunhan.presentation.detail.DetailViewModel

@Composable
fun DetailRoute(
    viewModel: DetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    DetailScreen(
        state = state,
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box {
            Text("${state.status} - ${state.text}")
        }
    }
}