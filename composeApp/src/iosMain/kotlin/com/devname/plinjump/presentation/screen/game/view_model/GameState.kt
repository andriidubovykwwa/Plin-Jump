package com.devname.plinjump.presentation.screen.game.view_model

import androidx.compose.ui.unit.IntSize

data class GameState(
    val score: Float = 0f,
    val coins: Int = 0,
    val playerY: Float = 0f,
    val obstaclesX: List<Float> = emptyList(),
    val coinsX: List<Float> = emptyList(),
    val canvasSize: IntSize = IntSize(800, 600),
    val blockSize: Float = 120f,
    val isPlayerJumping: Boolean = false,
    val isGameActive: Boolean = false,
    val isPlayerCrushed: Boolean = false,
)
