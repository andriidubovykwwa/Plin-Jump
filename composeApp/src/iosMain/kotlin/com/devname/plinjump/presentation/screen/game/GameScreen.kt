package com.devname.plinjump.presentation.screen.game

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.MovingBackground
import com.devname.plinjump.presentation.component.ObstacleGameComponent
import com.devname.plinjump.presentation.component.PlayerBallGameComponent
import com.devname.plinjump.presentation.screen.game.view_model.GameEvent
import com.devname.plinjump.presentation.screen.game.view_model.GameViewModel
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.OrientationManager
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

    LaunchedEffect(key1 = Unit) {
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
            gameSize = state.canvasSize,
            blockSize = state.blockSize,
            playerY = state.playerY
        )
        ObstacleGameComponent(
            gameSize = state.canvasSize,
            blockSize = state.blockSize,
            obstacleX = state.obstacleX
        )
        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "Score: ${state.score.toInt()}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 25.sp,
        )
        if (!state.isGameActive) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = { onEvent(GameEvent.StartGame) }
            ) {
                Text(
                    text = stringResource(if (state.isPlayerCrushed) Res.string.restart else Res.string.start),
                )
            }
        }
    }
}
