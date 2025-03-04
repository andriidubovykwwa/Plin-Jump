package com.devname.plinjump.presentation.screen.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.MovingBackground
import com.devname.plinjump.presentation.screen.game.view_model.GameEvent
import com.devname.plinjump.presentation.screen.game.view_model.GameViewModel
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.OrientationManager
import com.devname.plinjump.utils.pxToDp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.obstacle_block
import plinjump.composeapp.generated.resources.player_block

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
        Image(
            modifier = Modifier.size(pxToDp(state.blockSize).dp).offset {
                IntOffset(
                    x = ((state.canvasSize.width - state.blockSize) / 2).toInt(),
                    y = (state.canvasSize.height - state.blockSize - state.playerY * state.canvasSize.height).toInt()
                )
            },
            painter = painterResource(Res.drawable.player_block),
            contentDescription = "Ball"
        )
        Image(
            modifier = Modifier.size(pxToDp(state.blockSize).dp).offset {
                IntOffset(
                    x = (state.canvasSize.width * state.obstacleX).toInt(),
                    y = (state.canvasSize.height - state.blockSize).toInt()
                )
            },
            painter = painterResource(Res.drawable.obstacle_block),
            contentDescription = "Obstacle"
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
                    text = if (state.isPlayerCrushed) "Restart" else "Start",
                )
            }
        }
    }
}
