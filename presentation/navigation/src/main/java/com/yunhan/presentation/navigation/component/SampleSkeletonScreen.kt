package com.yunhan.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yunhan.presentation.designsystem.component.shimmer.CommonShimmerItem

@Composable
fun SampleSkeletonScreen(

) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        CommonShimmerItem(
            modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)).fillMaxWidth().height(100.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        CommonShimmerItem(
            modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)).fillMaxWidth().height(400.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        CommonShimmerItem(
            modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)).fillMaxWidth().height(300.dp)
        )
    }
}