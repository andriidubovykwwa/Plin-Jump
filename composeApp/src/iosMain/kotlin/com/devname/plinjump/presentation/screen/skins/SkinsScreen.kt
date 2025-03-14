package com.devname.plinjump.presentation.screen.skins

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.devname.plinjump.utils.SoundManager
import com.devname.plinjump.utils.dialogGradientColor1
import com.devname.plinjump.utils.dialogGradientColor2
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.back
import plinjump.composeapp.generated.resources.bg_menu
import plinjump.composeapp.generated.resources.icon_back
import plinjump.composeapp.generated.resources.skins

@Composable
fun SkinsScreen(navController: NavController, viewModel: SkinsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    var showElements by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showElements = true }
    Column(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.bg_menu),
                contentScale = ContentScale.Crop
            )
    ) {
        Row(
            Modifier.fillMaxWidth().safeContentPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.width(32.dp).clickable {
                    SoundManager.playButtonClick(state.sound)
                    navController.popBackStack()
                },
                painter = painterResource(Res.drawable.icon_back),
                contentDescription = stringResource(Res.string.back),
                contentScale = ContentScale.FillWidth,
            )
            GameText(text = stringResource(Res.string.skins), fontSize = 38.sp)
            Spacer(Modifier.width(32.dp))
        }
        val dialogShape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)
        AnimatedVisibility(
            visible = showElements,
            enter = slideInVertically(
                // Slide from the bottom
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = fadeOut()
        ) {
            LazyColumn(
                Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            dialogGradientColor1,
                            dialogGradientColor2
                        )
                    ),
                    dialogShape
                ).border(3.dp, Color.White, dialogShape),
                contentPadding = WindowInsets.safeContent.asPaddingValues(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    CoinDisplay(coins = state.coins)
                }
                val itemsInRow = 2
                val size = GameConfig.Skin.entries.size
                var rows = size / itemsInRow
                if (size % 2 > 0) rows += 1
                items(rows) { row ->
                    Row(
                        Modifier.fillMaxSize().padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(itemsInRow) { rowIndex ->
                            val index = row * itemsInRow + rowIndex
                            if (index < size) {
                                val skin = GameConfig.Skin.entries[index]
                                SkinComponent(
                                    Modifier,
                                    res = skin.res,
                                    index = index,
                                    price = skin.price,
                                    coins = state.coins,
                                    onBuy = {
                                        SoundManager.playButtonClick(state.sound)
                                        onEvent(SkinsEvent.BuySkin(index))
                                    },
                                    isSelected = state.selectedSkinIndex == index,
                                    onSelect = {
                                        SoundManager.playButtonClick(state.sound)
                                        onEvent(SkinsEvent.SelectSkin(index))
                                    },
                                    isAvailable = state.skinsStatuses[index]
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}