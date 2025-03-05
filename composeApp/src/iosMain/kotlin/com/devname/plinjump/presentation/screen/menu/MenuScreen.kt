package com.devname.plinjump.presentation.screen.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.devname.plinjump.presentation.navigation.Screen
import com.devname.plinjump.presentation.screen.SharedData
import com.devname.plinjump.presentation.screen.menu.view_model.MenuViewModel
import com.devname.plinjump.utils.OrientationManager
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: MenuViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val sharedHighScore by SharedData.highScore.collectAsState(null)
    LaunchedEffect(Unit) {
        OrientationManager().orientation = OrientationManager.Orientation.ALL
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Button(onClick = { navController.navigate(Screen.Game) }) {
                Text("Play")
            }
            Button(onClick = { navController.navigate(Screen.Info) }) {
                Text("Info")
            }
            Button(onClick = { navController.navigate(Screen.Shop) }) {
                Text("Shop")
            }
            Button(onClick = { navController.navigate(Screen.Skins) }) {
                Text("Skins")
            }
            Text("High Score: ${(sharedHighScore ?: state.highScore)}")
        }
    }
}