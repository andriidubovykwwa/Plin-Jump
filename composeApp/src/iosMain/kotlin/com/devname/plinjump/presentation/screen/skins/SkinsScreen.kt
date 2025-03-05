package com.devname.plinjump.presentation.screen.skins

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.screen.skins.view_model.SkinsEvent
import com.devname.plinjump.presentation.screen.skins.view_model.SkinsViewModel
import com.devname.plinjump.utils.GameConfig
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SkinsScreen(navController: NavController, viewModel: SkinsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
        Text("Skins")
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
            item {
                Text(text = "Skins")
            }
            item {
                Text(text = "Coins: ${state.coins}")
            }
            items(GameConfig.Skin.entries.size) { index ->
                val skin = GameConfig.Skin.entries[index]
                Column(Modifier.padding(10.dp).background(Color.Yellow).padding(10.dp)) {
                    Image(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(skin.res),
                        contentDescription = "Skin $index",
                        contentScale = ContentScale.FillBounds
                    )
                    val skinStatus = state.skinsStatuses[index]
                    Text("Selected: ${index == state.selectedSkinIndex}")
                    Button(
                        onClick = {
                            if (skinStatus) onEvent(SkinsEvent.SelectSkin(index))
                            else onEvent(SkinsEvent.BuySkin(index))
                        }
                    ) {
                        Text(
                            text = if (skinStatus) "Select" else "Buy (${skin.price})"
                        )
                    }
                }
            }
        }
    }
}