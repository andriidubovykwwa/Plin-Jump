package com.devname.plinjump.presentation.screen.skins

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.CoinDisplay
import com.devname.plinjump.presentation.component.GameText
import com.devname.plinjump.presentation.component.SkinComponent
import com.devname.plinjump.presentation.screen.skins.view_model.SkinsEvent
import com.devname.plinjump.presentation.screen.skins.view_model.SkinsViewModel
import com.devname.plinjump.utils.GameConfig
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.back
import plinjump.composeapp.generated.resources.bg_shop
import plinjump.composeapp.generated.resources.icon_back
import plinjump.composeapp.generated.resources.skins

@Composable
fun SkinsScreen(navController: NavController, viewModel: SkinsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    Box(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.bg_shop),
                contentScale = ContentScale.Crop
            )
    ) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = WindowInsets.safeContent.asPaddingValues(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.width(32.dp).clickable {
                            navController.popBackStack()
                        },
                        painter = painterResource(Res.drawable.icon_back),
                        contentDescription = stringResource(Res.string.back),
                        contentScale = ContentScale.FillWidth,
                    )
                    GameText(text = stringResource(Res.string.skins), fontSize = 30.sp)
                    Spacer(Modifier.width(32.dp))
                }
            }
            item {
                CoinDisplay(coins = state.coins)
            }
            items(GameConfig.Skin.entries.size) { index ->
                val skin = GameConfig.Skin.entries[index]
                SkinComponent(
                    Modifier.fillMaxSize(),
                    res = skin.res,
                    index = index,
                    price = skin.price,
                    coins = state.coins,
                    onBuy = { onEvent(SkinsEvent.BuySkin(index)) },
                    isSelected = state.selectedSkinIndex == index,
                    onSelect = { onEvent(SkinsEvent.SelectSkin(index)) },
                    isAvailable = state.skinsStatuses[index]
                )
            }
        }
    }
}