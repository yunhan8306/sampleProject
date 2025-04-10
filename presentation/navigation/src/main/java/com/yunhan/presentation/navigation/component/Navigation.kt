package com.yunhan.presentation.navigation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yunhan.presentation.sample.SampleNavType

@Composable
fun Navigation(
    navController: NavHostController
) {

    val navList = SampleNavType.entries
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        navList.forEach { navType ->
            val selected = navType.name == currentRoute

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(navType.name) },
                icon = {},
                label = { Text(navType.name) }
            )
        }
    }
}