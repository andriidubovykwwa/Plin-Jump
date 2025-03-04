package com.devname.plinjump.presentation.screen.game.view_model

import androidx.compose.ui.unit.IntSize

sealed interface GameEvent {
    data object Jump : GameEvent
    data object StartGame : GameEvent
    data object SecondTick : GameEvent
    data object ActivateShield : GameEvent
    data object ActivateFireball : GameEvent
    data class UpdateGame(val time: Long) : GameEvent
    data class SetSizeData(val canvasSize: IntSize, val blockSize: Float) : GameEvent
}