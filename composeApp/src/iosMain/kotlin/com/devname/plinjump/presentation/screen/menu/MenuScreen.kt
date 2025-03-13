package com.devname.plinjump.presentation.screen.menu

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.AdaptiveContainer
import com.devname.plinjump.presentation.component.DailyQuestDialog
import com.devname.plinjump.presentation.component.GameText
import com.devname.plinjump.presentation.component.SettingsDialog
import com.devname.plinjump.presentation.navigation.Screen
import com.devname.plinjump.presentation.screen.SharedData
import com.devname.plinjump.presentation.screen.menu.view_model.MenuEvent
import com.devname.plinjump.presentation.screen.menu.view_model.MenuViewModel
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.OrientationManager
import com.devname.plinjump.utils.SoundManager
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.app_name
import plinjump.composeapp.generated.resources.app_title
import plinjump.composeapp.generated.resources.ball
import plinjump.composeapp.generated.resources.bg_menu
import plinjump.composeapp.generated.resources.daily_quests
import plinjump.composeapp.generated.resources.high_score
import plinjump.composeapp.generated.resources.info
import plinjump.composeapp.generated.resources.info_button
import plinjump.composeapp.generated.resources.play_button
import plinjump.composeapp.generated.resources.round_button_bg
import plinjump.composeapp.generated.resources.settings
import plinjump.composeapp.generated.resources.settings_button
import plinjump.composeapp.generated.resources.shop
import plinjump.composeapp.generated.resources.shop_button
import plinjump.composeapp.generated.resources.start

@Composable
fun MenuScreen(navController: NavController, viewModel: MenuViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val sharedHighScore by SharedData.highScore.collectAsState(null)
    val sharedSelectedSkinIndex by SharedData.selectedSkinIndex.collectAsState(null)
    var isSettingsOpened by remember { mutableStateOf(false) }
    var isDailyQuestsOpened by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        onEvent(MenuEvent.ProcessDailyDataReset)
        SoundManager.playFire(0)
        OrientationManager().orientation = OrientationManager.Orientation.ALL
    }
    LaunchedEffect(state.music) {
        SoundManager.playMusic(state.music)
    }
    Box(
        Modifier.fillMaxSize().paint(
            painter = painterResource(Res.drawable.bg_menu),
            contentScale = ContentScale.Crop
        ).safeContentPadding(), contentAlignment = Alignment.Center
    ) {
        AdaptiveContainer(Modifier.fillMaxSize().align(Alignment.Center)) {
            val infiniteTransition = rememberInfiniteTransition()
            val yPosition by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = -70f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600,
                        easing = LinearOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Image(
                modifier = Modifier.height(100.dp).offset(y = yPosition.dp),
                painter = painterResource(Res.drawable.app_title),
                contentDescription = stringResource(Res.string.app_name),
                contentScale = ContentScale.FillHeight,
            )
            Image(
                modifier = Modifier.size(150.dp).clip(CircleShape).clickable {
                    SoundManager.playButtonClick(state.sound)
                    navController.navigate(Screen.Game)
                },
                painter = painterResource(Res.drawable.play_button),
                contentDescription = stringResource(Res.string.start),
                contentScale = ContentScale.FillBounds,
            )
            val shape = RoundedCornerShape(10.dp)
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.background(Color(0xff441768), shape)
                        .border(3.dp, Color.White, shape)
                        .padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    GameText(
                        text = stringResource(Res.string.high_score),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    GameText(text = "${(sharedHighScore ?: state.highScore)}", fontSize = 20.sp)
                }
                Box(
                    Modifier.border(3.dp, Color(0xff55cb4f), shape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xff0A743E),
                                    Color(0xff3DA737),
                                )
                            ),
                            shape
                        )
                        .clip(shape)
                        .clickable { isDailyQuestsOpened = true }
                        .padding(5.dp),
                ) {
                    GameText(text = stringResource(Res.string.daily_quests), fontSize = 16.sp)
                }
            }
        }
        AdaptiveContainer(
            Modifier.align(Alignment.TopEnd),
            space = 5.dp
        ) {
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape).clickable {
                    SoundManager.playButtonClick(state.sound)
                    isSettingsOpened = true
                },
                painter = painterResource(Res.drawable.settings_button),
                contentDescription = stringResource(Res.string.settings),
                contentScale = ContentScale.FillBounds,
            )
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape).clickable {
                    SoundManager.playButtonClick(state.sound)
                    navController.navigate(Screen.Info)
                },
                painter = painterResource(Res.drawable.info_button),
                contentDescription = stringResource(Res.string.info),
                contentScale = ContentScale.FillBounds,
            )
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape).clickable {
                    SoundManager.playButtonClick(state.sound)
                    navController.navigate(Screen.Shop)
                },
                painter = painterResource(Res.drawable.shop_button),
                contentDescription = stringResource(Res.string.shop),
                contentScale = ContentScale.FillBounds,
            )
            Box(
                modifier = Modifier.size(40.dp).paint(
                    painter = painterResource(Res.drawable.round_button_bg),
                    contentScale = ContentScale.FillBounds
                ).clip(CircleShape).clickable {
                    SoundManager.playButtonClick(state.sound)
                    navController.navigate(Screen.Skins)
                },
                contentAlignment = Alignment.Center
            ) {
                val skinIndex = sharedSelectedSkinIndex ?: state.selectedSkinIndex
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(GameConfig.Skin.entries[skinIndex].res),
                    contentDescription = stringResource(Res.string.ball),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
    if (isSettingsOpened) {
        SettingsDialog(
            music = state.music,
            sound = state.sound,
            onSetMusic = { onEvent(MenuEvent.SetMusic(it)) },
            onSetSound = { onEvent(MenuEvent.SetSound(it)) },
            onDismiss = { isSettingsOpened = false }
        )
    }
    if (isDailyQuestsOpened) {
        DailyQuestDialog(
            playedDailyGames = state.playedDailyGames,
            dailyRecord = state.dailyRecord,
            onDismiss = { isDailyQuestsOpened = false },
            claimed = state.claimedQuests,
            onClaim = { onEvent(MenuEvent.ClaimQuest(it)) }
        )
    }
}