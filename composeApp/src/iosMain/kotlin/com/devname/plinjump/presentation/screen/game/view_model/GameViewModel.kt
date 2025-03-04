package com.devname.plinjump.presentation.screen.game.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.utils.GameConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private var prevTime by mutableStateOf(0L)

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.Jump -> processJump()
            is GameEvent.SetSizeData -> processSetSizeData(event.canvasSize, event.blockSize)
            is GameEvent.StartGame -> processStartGame()
            is GameEvent.UpdateGame -> processUpdateGame(event.time)
        }
    }

    private fun processJump() = viewModelScope.launch {
        if (!state.value.isGameActive) return@launch
        if (state.value.playerY > 0) return@launch
        _state.update { it.copy(isPlayerJumping = true) }
    }

    private fun processSetSizeData(canvasSize: IntSize, blockSize: Float) = viewModelScope.launch {
        _state.update {
            it.copy(canvasSize = canvasSize, blockSize = blockSize)
        }
    }

    private fun processStartGame() = viewModelScope.launch {
        _state.update {
            it.copy(
                isGameActive = true,
                isPlayerCrushed = true,
                isPlayerJumping = false,
                playerY = 0f,
                obstacleX = 2f,
                score = 0f
            )
        }
    }

    private fun processUpdateGame(time: Long) = viewModelScope.launch {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        if (!state.value.isGameActive) return@launch

        handleJumpUp(floatDelta)
        handleJumpDown(floatDelta)
        handleObstacles(floatDelta)
        handleCollisions()
        handleScore(floatDelta)
    }

    private fun handleScore(floatDelta: Float) {
        _state.update {
            it.copy(score = it.score + floatDelta * GameConfig.SCORE_INCREASE_SPEED)
        }
    }

    private fun handleJumpUp(floatDelta: Float) {
        if (state.value.isPlayerJumping) {
            val newY = state.value.playerY + getActualJumpSpeed(floatDelta)
            _state.update {
                it.copy(playerY = newY, isPlayerJumping = newY < GameConfig.JUMP_HEIGHT)
            }
        }
    }

    private fun handleJumpDown(floatDelta: Float) {
        if (!state.value.isPlayerJumping && state.value.playerY > 0) {
            val newY = state.value.playerY - getActualJumpSpeed(floatDelta)
            _state.update {
                it.copy(playerY = if (newY > 0) newY else 0f)
            }
        }
    }

    private fun handleCollisions() {
        val blockSize = state.value.blockSize
        val playerY = state.value.playerY * state.value.canvasSize.height
        val obstacleX = state.value.obstacleX * state.value.canvasSize.width
        val centerX = state.value.canvasSize.width * 0.5f
        // Player is too high
        if (playerY > blockSize) return
        // Player is in left from obstacle
        if (centerX + blockSize / 2 < obstacleX) return
        // Player is in right from obstacle
        if (centerX - blockSize / 2 > obstacleX + blockSize) return

        // Collision:
        lose()
    }

    private fun lose() {
        // TODO: record result
        _state.update {
            it.copy(isGameActive = false, isPlayerCrushed = true)
        }
    }

    private fun handleObstacles(floatDelta: Float) {
        val newX = state.value.obstacleX - getActualObstacleMoveSpeed(floatDelta)
        val maxObstacleStart = 1.8f
        val minObstacleStart = 1.15f
        val destroyObstacleX = -0.15f
        _state.update {
            it.copy(
                obstacleX = if (newX > destroyObstacleX) {
                    newX
                } else {
                    Random.nextFloat() * (maxObstacleStart - minObstacleStart) + minObstacleStart
                }
            )
        }
    }

    private fun getActualObstacleMoveSpeed(floatDelta: Float): Float {
        val scoreSpeedMultiplier = 1f + state.value.score / 220f
        return floatDelta * GameConfig.DEFAULT_OBSTACLE_MOVE_SPEED * scoreSpeedMultiplier
    }

    private fun getActualJumpSpeed(floatDelta: Float): Float {
        val playerY = state.value.playerY
        val heightSpeedMultiplier =
            1f + ((GameConfig.JUMP_HEIGHT - playerY) / GameConfig.JUMP_HEIGHT * 2f)
        return floatDelta * GameConfig.JUMP_SPEED * heightSpeedMultiplier
    }
}