package com.yunhan.presentation.sample.component

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yunhan.presentation.base.BaseStatus
import com.yunhan.presentation.designsystem.component.loading.CommonLoadingDialog
import com.yunhan.presentation.detail.DetailActivity
import com.yunhan.presentation.sample.SampleAction
import com.yunhan.presentation.sample.SampleNavType
import com.yunhan.presentation.sample.SampleSideEffect
import com.yunhan.presentation.sample.SampleState
import com.yunhan.presentation.sample.SampleViewModel
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.sampleScreen(
    sampleNavType: SampleNavType
) {
    composable(
        route = sampleNavType.name
    ) {
        SampleRoute(
            sampleNavType = sampleNavType
        )
    }
}

@Composable
fun SampleRoute(
    viewModel: SampleViewModel = hiltViewModel(),
    sampleNavType: SampleNavType
) {

    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.state.collectAsState()
    val isShowLoading by viewModel.isShowLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setSampleNav(sampleNavType)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when(sideEffect) {
                is SampleSideEffect.StartDetailActivity -> {
                    Intent(activity, DetailActivity::class.java)
                        .putExtra("from", sideEffect.sampleNavType.name)
                        .apply { activity.startActivity(this) }
                }
                else -> Unit
            }
        }
    }

    when(state.status) {
        BaseStatus.Idle -> Unit
        BaseStatus.Rendering -> {
            SampleSkeletonScreen()
        }
        BaseStatus.Complete -> {
            SampleScreen(
                state = state,
                onAction = viewModel::onAction
            )
        }
        else -> Unit
    }

    CommonLoadingDialog(
        isLoading = isShowLoading,
        onDismiss = { viewModel.onAction(SampleAction.CancelLoading) }
    )
}

@Composable
fun SampleScreen(
    state: SampleState,
    onAction: (SampleAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(20.dp)
                .clickable { onAction.invoke(SampleAction.StartDetailActivity(state.sampleNavType)) }
        ) {
            Text("${state.status} - ${state.sampleNavType.name}")
        }
    }
}