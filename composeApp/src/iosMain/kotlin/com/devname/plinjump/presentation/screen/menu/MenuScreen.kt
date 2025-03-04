package com.devname.plinjump.presentation.screen.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.devname.plinjump.presentation.navigation.Screen
import com.devname.plinjump.utils.OrientationManager

@Composable
fun MenuScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        OrientationManager().orientation = OrientationManager.Orientation.ALL
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { navController.navigate(Screen.Game) }) {
            Text("Play")
        }
    }
}