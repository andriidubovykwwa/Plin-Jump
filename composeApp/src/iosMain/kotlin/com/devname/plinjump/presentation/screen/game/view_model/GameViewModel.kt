package com.devname.plinjump.presentation.screen.game.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.domain.GameRepository
import com.devname.plinjump.utils.GameConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    private var prevTime by mutableStateOf(0L)


    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    shields = gameRepository.getShields(),
                    fireballs = gameRepository.getFireballs()
                )
            }
        }
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.Jump -> processJump()
            is GameEvent.SetSizeData -> processSetSizeData(event.canvasSize, event.blockSize)
            is GameEvent.StartGame -> processStartGame()
            is GameEvent.UpdateGame -> processUpdateGame(event.time)
            is GameEvent.SecondTick -> processSecondTick()
            is GameEvent.ActivateFireball -> processActivateFireball()
            is GameEvent.ActivateShield -> processActivateShield()
        }
    }

    private fun processActivateShield() = viewModelScope.launch {
        if (!state.value.isGameActive) return@launch
        if (state.value.shields <= 0) return@launch
        val newShields = state.value.shields - 1
        gameRepository.setShields(newShields)
        _state.update {
            it.copy(shields = newShields, shieldSeconds = GameConfig.SHIELD_SECONDS)
        }
    }

    private fun processActivateFireball() = viewModelScope.launch {
        if (!state.value.isGameActive) return@launch
        if (state.value.fireballs <= 0) return@launch
        val newFireballs = state.value.fireballs
        gameRepository.setFireballs(newFireballs)
        _state.update {
            it.copy(fireballs = newFireballs, fireballSeconds = GameConfig.FIREBALL_SECONDS)
        }
    }

    private fun processSecondTick() = viewModelScope.launch {
        if (!state.value.isGameActive) return@launch
        _state.update {
            it.copy(
                shieldSeconds = maxOf(it.shieldSeconds - 1, 0),
                fireballSeconds = maxOf(it.fireballSeconds - 1, 0),
            )
        }
    }

    private fun processJump() = viewModelScope.launch {
        if (!state.value.isGameActive) return@launch
        if (state.value.playerY > 0) return@launch
        _state.update { it.copy(isPlayerJumping = true) }
    }

    private fun processSetSizeData(canvasSize: IntSize, blockSize: Float) = viewModelScope.launch {
        _state.update {
            it.copy(gameSize = canvasSize, blockSize = blockSize)
        }
    }

    private fun processStartGame() = viewModelScope.launch {
        _state.update {
            GameState(
                gameSize = it.gameSize,
                blockSize = it.blockSize,
                isGameActive = true,
                shields = it.shields,
                fireballs = it.fireballs
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
        handleObjects(floatDelta)
        handleCoinsCollision()
        handleObstaclesCollisions()
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

    private suspend fun handleObstaclesCollisions() {
        val blockSize = state.value.blockSize
        val playerY = state.value.playerY * state.value.gameSize.height
        val centerX = state.value.gameSize.width * 0.5f

        var collidedObstacleX: Float? = null
        state.value.obstaclesX.forEach {
            if (collidedObstacleX == null) {
                val obstacleX = it * state.value.gameSize.width
                var isCollision = true
                // Player is too high
                if (playerY > blockSize) isCollision = false
                // Player is in left from obstacle
                if (centerX + blockSize / 2 < obstacleX) isCollision = false
                // Player is in right from obstacle
                if (centerX - blockSize / 2 > obstacleX + blockSize) isCollision = false

                if (isCollision) collidedObstacleX = it
            }
        }
        if (collidedObstacleX != null) {
            if (state.value.fireballSeconds > 0) {
                _state.update {
                    it.copy(
                        obstaclesX = it.obstaclesX.filter { x -> x != collidedObstacleX },
                    )
                }
            } else if (state.value.shieldSeconds > 0) {
                _state.update {
                    it.copy(
                        obstaclesX = it.obstaclesX.filter { x -> x != collidedObstacleX },
                        shieldSeconds = 0
                    )
                }
            } else {
                lose()
            }
        }
    }

    private fun handleCoinsCollision() {
        val blockSize = state.value.blockSize
        val playerY = state.value.playerY * state.value.gameSize.height
        val centerX = state.value.gameSize.width * 0.5f

        var collidedCoinX: Float? = null
        state.value.coinsX.forEach {
            if (collidedCoinX == null) {
                val coinX = it * state.value.gameSize.width
                var isCollision = true
                // Player is too high
                if (playerY > blockSize) isCollision = false
                // Player is in left from obstacle
                if (centerX + blockSize / 2 < coinX) isCollision = false
                // Player is in right from obstacle
                if (centerX - blockSize / 2 > coinX + blockSize) isCollision = false

                if (isCollision) collidedCoinX = it
            }
        }
        if (collidedCoinX != null) {
            _state.update {
                it.copy(
                    coinsX = it.coinsX.filter { x -> x != collidedCoinX },
                    coins = it.coins + 1
                )
            }
        }
    }

    private suspend fun lose() {
        gameRepository.processScore(state.value.score.toInt())
        gameRepository.setCoins(gameRepository.getCoins() + state.value.coins)
        _state.update {
            it.copy(isGameActive = false, isPlayerCrushed = true)
        }
    }

    private fun handleObjects(floatDelta: Float) {
        val moveSpeed = getActualObstacleMoveSpeed(floatDelta)
        val destroyObjectX = -0.15f
        val maxObstacleStart = 1.8f
        val minObstacleStart = 1.4f
        val minObstacleDistance = 0.65f
        val maxObstacleDistance = 0.88f
        val coinObstacleDistance = 0.3f
        val secondObstacleChance = 0.5f

        val updatedObstacles = state.value.obstaclesX
            .map { it - moveSpeed }
            .filter { it > destroyObjectX }
            .toMutableList()
        val updatedCoins = state.value.coinsX
            .map { it - moveSpeed }
            .filter { it > destroyObjectX }
            .toMutableList()

        // If all obstacles are off-screen, spawn new ones
        if (updatedObstacles.isEmpty()) {
            val newObstacles = mutableListOf<Float>()
            val newCoins = mutableListOf<Float>()
            val firstObstacleX =
                Random.nextFloat() * (maxObstacleStart - minObstacleStart) + minObstacleStart
            val firstCoinX = firstObstacleX - coinObstacleDistance
            newCoins.add(firstCoinX)
            newObstacles.add(firstObstacleX)

            // Chance to spawn a second obstacle
            if (Random.nextFloat() < secondObstacleChance) {
                val secondObstacleX = firstObstacleX + minObstacleDistance +
                        Random.nextFloat() * (maxObstacleDistance - minObstacleDistance)

                newObstacles.add(secondObstacleX)

                val middleCoinX = (firstObstacleX + secondObstacleX) / 2
                newCoins.add(middleCoinX)

                val lastCoinX = secondObstacleX + coinObstacleDistance
                newCoins.add(lastCoinX)
            } else {
                val lastCoinX = firstObstacleX + coinObstacleDistance
                newCoins.add(lastCoinX)
            }
            updatedCoins.addAll(newCoins)
            updatedObstacles.addAll(newObstacles)
        }

        _state.update { it.copy(obstaclesX = updatedObstacles, coinsX = updatedCoins) }
    }


    private fun getActualObstacleMoveSpeed(floatDelta: Float): Float {
        val scoreSpeedMultiplier = 1f + state.value.score / GameConfig.SPEED_X2_SCORE_THRESHOLD
        return floatDelta * GameConfig.DEFAULT_OBSTACLE_MOVE_SPEED * scoreSpeedMultiplier
    }

    private fun getActualJumpSpeed(floatDelta: Float): Float {
        val playerY = state.value.playerY
        val heightSpeedMultiplier =
            1f + ((GameConfig.JUMP_HEIGHT - playerY) / GameConfig.JUMP_HEIGHT * 2f)
        return floatDelta * GameConfig.JUMP_SPEED * heightSpeedMultiplier
    }
}