package com.devname.plinjump.presentation.screen.game

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.CoinObjectGameComponent
import com.devname.plinjump.presentation.component.MovingBackground
import com.devname.plinjump.presentation.component.ObstacleGameComponent
import com.devname.plinjump.presentation.component.PlayerBallGameComponent
import com.devname.plinjump.presentation.screen.game.view_model.GameEvent
import com.devname.plinjump.presentation.screen.game.view_model.GameViewModel
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.OrientationManager
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.restart
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

    Box(
        Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val blockSize = GameConfig.BLOCK_SIZE * it.size.height
                onEvent(GameEvent.SetSizeData(it.size, blockSize))
            }
            .pointerInput(Unit) { detectTapGestures { onEvent(GameEvent.Jump) } }
    ) {
        MovingBackground(isAnimationRunning = state.isGameActive)
        PlayerBallGameComponent(
            gameSize = state.gameSize,
            blockSize = state.blockSize,
            playerY = state.playerY,
            selectedSkinIndex = state.selectedSkinIndex
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
        Column(
            Modifier.align(Alignment.TopStart),
            horizontalAlignment = Alignment.Start,
        ) {
            Button(onClick = { onEvent(GameEvent.ActivateShield) }) {
                Text("Shield (${state.shields})", fontSize = 14.sp)
            }
            Button(onClick = { onEvent(GameEvent.ActivateFireball) }) {
                Text("Fireball (${state.fireballs})", fontSize = 14.sp)
            }
        }
        Column(
            Modifier.align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = "Score: ${state.score.toInt()}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
            )
            Text(
                text = "Coins: ${state.coins}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
            )
            Text(
                text = "Shield: ${state.shieldSeconds}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
            )
            Text(
                text = "Fireball: ${state.fireballSeconds}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
            )
        }
        if (!state.isGameActive) {
            if (state.isPlayerCrushed) {
                Dialog(onDismissRequest = {}) {
                    Column(
                        Modifier.background(Color.White).padding(20.dp)
                    ) {
                        Button(onClick = { onEvent(GameEvent.StartGame) }) {
                            Text(stringResource(Res.string.restart))
                        }
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Back")
                        }
                    }
                }
            } else {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { onEvent(GameEvent.StartGame) }
                ) {
                    Text(
                        text = stringResource(Res.string.start),
                    )
                }
            }
        }
    }
}
