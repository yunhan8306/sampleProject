package com.yunhan.presentation.sample.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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

    LaunchedEffect(Unit) {
        viewModel.setSampleNav(sampleNavType)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when(sideEffect) {
                is SampleSideEffect.StartDetailActivity -> {
                    sideEffect.intent.apply { activity.startActivity(this) }
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
        isLoading = state.isLoading,
        onDismiss = { viewModel.onAction(SampleAction.CancelLoading) }
    )
}

@Composable
fun SampleScreen(
    state: SampleState,
    onAction: (SampleAction) -> Unit
) {
    val activity = LocalContext.current as ComponentActivity

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(20.dp)
                .clickable { onAction.invoke(SampleAction.StartDetailActivity(activity, state.sampleNavType)) }
        ) {
            Text("${state.status} - ${state.sampleNavType.name}")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text("cnt - ${state.cnt}")
        Spacer(modifier = Modifier.height(50.dp))
        Row {
            Box(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(30.dp)
            ) {
                Text("down")
            }
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(30.dp)
                    .clickable { onAction.invoke(SampleAction.SendUp) }
            ) {
                Text("up")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}