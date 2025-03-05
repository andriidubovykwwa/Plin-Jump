package com.devname.plinjump.presentation.screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

object SharedData {
    private val _highScore = MutableStateFlow<Int?>(null)
    val highScore = _highScore.asSharedFlow()


    fun updateHighScore(value: Int) {
        _highScore.update { value }
    }
}