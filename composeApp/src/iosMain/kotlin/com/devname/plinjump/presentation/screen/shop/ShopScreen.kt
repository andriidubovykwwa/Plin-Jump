package com.devname.plinjump.presentation.screen.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.screen.shop.view_model.ShopEvent
import com.devname.plinjump.presentation.screen.shop.view_model.ShopViewModel
import com.devname.plinjump.utils.GameConfig
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShopScreen(navController: NavController, viewModel: ShopViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
        Text("Shop")
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
            item {
                Text(text = "Coins: ${state.coins}")
            }
            item {
                Text(text = "Shields: ${state.shields}")
            }
            item {
                Text(text = "Fireballs: ${state.fireballs}")
            }
            item {
                Button(onClick = { onEvent(ShopEvent.BuyShield) }, enabled = state.canBuyShield) {
                    Text("Buy Shield (${GameConfig.SHIELD_PRICE})")
                }
            }
            item {
                Button(
                    onClick = { onEvent(ShopEvent.BuyFireball) },
                    enabled = state.canBuyFireball
                ) {
                    Text("Buy Shield (${GameConfig.FIREBALL_PRICE})")
                }
            }
        }
    }
}