package com.devname.plinjump.presentation.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.BonusComponent
import com.devname.plinjump.presentation.component.CoinDisplay
import com.devname.plinjump.presentation.component.CoinObjectGameComponent
import com.devname.plinjump.presentation.component.GameOverDialog
import com.devname.plinjump.presentation.component.MovingBackground
import com.devname.plinjump.presentation.component.ObstacleGameComponent
import com.devname.plinjump.presentation.component.PauseDialog
import com.devname.plinjump.presentation.component.PlatformComponent
import com.devname.plinjump.presentation.component.PlayerBallGameComponent
import com.devname.plinjump.presentation.component.ScoreDisplay
import com.devname.plinjump.presentation.screen.game.view_model.GameEvent
import com.devname.plinjump.presentation.screen.game.view_model.GameViewModel
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.OrientationManager
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.icon_pause
import plinjump.composeapp.generated.resources.pause
import plinjump.composeapp.generated.resources.play_button
import plinjump.composeapp.generated.resources.start

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    DisposableEffect(Unit) {
        val orientationManager = OrientationManager()
        orientationManager.orientation = OrientationManager.Orientation.LANDSCAPE
        onDispose {
            orientationManager.orientation = OrientationManager.Orientation.ALL
        }
    }

    LaunchedEffect(state.isGameActive) {
        while (state.isGameActive) {
            delay(1000)
            onEvent(GameEvent.SecondTick)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { onEvent(GameEvent.UpdateGame(it)) }
        }
    }

    Box(Modifier.fillMaxSize()) {
        MovingBackground(isAnimationRunning = state.isGameActive && !state.isGamePaused)
        Column(
            Modifier.fillMaxSize()
                .pointerInput(Unit) { detectTapGestures { onEvent(GameEvent.Jump) } }
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(9f)
                    .onGloballyPositioned {
                        val blockSize = GameConfig.BLOCK_SIZE * it.size.height
                        onEvent(GameEvent.SetSizeData(it.size, blockSize))
                    }
            ) {
                PlayerBallGameComponent(
                    gameSize = state.gameSize,
                    blockSize = state.blockSize,
                    playerY = state.playerY,
                    selectedSkinIndex = state.selectedSkinIndex,
                    rotation = (state.score * 100) % 360f,
                    isShieldActive = state.shieldSeconds > 0,
                    isFireballActive = state.fireballSeconds > 0,
                )
                state.obstaclesX.forEach { obstacleX ->
                    ObstacleGameComponent(
                        gameSize = state.gameSize,
                        blockSize = state.blockSize,
                        obstacleX = obstacleX
                    )
                }
                state.coinsX.forEach { coinX ->
                    CoinObjectGameComponent(
                        gameSize = state.gameSize,
                        blockSize = state.blockSize,
                        coinX = coinX
                    )
                }
            }
            PlatformComponent(
                Modifier.weight(1f),
                isAnimationRunning = state.isGameActive && !state.isGamePaused
            )
        }
        // UI box
        Box(Modifier.fillMaxSize().safeContentPadding()) {
            Image(
                modifier = Modifier.align(Alignment.TopStart).size(30.dp)
                    .clickable { onEvent(GameEvent.TogglePause) },
                painter = painterResource(Res.drawable.icon_pause),
                contentDescription = stringResource(Res.string.pause),
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ScoreDisplay(Modifier.widthIn(min = 80.dp), score = state.score.toInt())
                CoinDisplay(Modifier.widthIn(min = 80.dp), coins = state.coins)
            }
            BonusComponent(
                Modifier.align(Alignment.TopCenter),
                onActivateShield = { onEvent(GameEvent.ActivateShield) },
                onActivateFireball = { onEvent(GameEvent.ActivateFireball) },
                shields = state.shields,
                fireballs = state.fireballs,
                shieldSeconds = state.shieldSeconds,
                fireballSeconds = state.fireballSeconds
            )
        }
        if (!state.isGameActive) {
            if (state.isPlayerCrushed) {
                GameOverDialog(
                    onBackHome = { navController.popBackStack() },
                    onRestart = { onEvent(GameEvent.StartGame) },
                    score = state.score.toInt(),
                    coins = state.coins
                )
            } else {
                Image(
                    modifier = Modifier.align(Alignment.Center).size(100.dp).clip(CircleShape)
                        .clickable { onEvent(GameEvent.StartGame) },
                    painter = painterResource(Res.drawable.play_button),
                    contentDescription = stringResource(Res.string.start),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        if (state.isGamePaused) {
            PauseDialog(
                onDismiss = { onEvent(GameEvent.TogglePause) },
                onBackHome = { navController.popBackStack() },
                onRestart = { onEvent(GameEvent.StartGame) },
            )
        }
    }
}
