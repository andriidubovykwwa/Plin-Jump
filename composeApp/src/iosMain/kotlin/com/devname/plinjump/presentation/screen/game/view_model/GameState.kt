package com.devname.plinjump.presentation.screen.game.view_model

import androidx.compose.ui.unit.IntSize
import com.devname.plinjump.utils.GameConfig

data class GameState(
    val score: Float = 0f,
    val coins: Int = 0,
    val playerY: Float = 0f,
    val obstaclesX: List<Float> = emptyList(),
    val coinsX: List<Float> = emptyList(),
    val shieldSeconds: Int = 0,
    val fireballSeconds: Int = 5,
    val gameSize: IntSize = IntSize(800, 600),
    val blockSize: Float = gameSize.height * GameConfig.BLOCK_SIZE,
    val isPlayerJumping: Boolean = false,
    val isGameActive: Boolean = false,
    val isPlayerCrushed: Boolean = false,
)
