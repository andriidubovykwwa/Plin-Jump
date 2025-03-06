package com.devname.plinjump.presentation.screen.menu.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.domain.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    highScore = gameRepository.getHighScore(),
                    selectedSkinIndex = gameRepository.getSelectedSkin(),
                    music = gameRepository.getMusic(),
                    sound = gameRepository.getSound()
                )
            }
        }
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SetMusic -> processSetMusic(event.value)
            is MenuEvent.SetSound -> processSetSound(event.value)
        }
    }

    private fun processSetMusic(value: Int) = viewModelScope.launch {
        gameRepository.setMusic(value)
        _state.update {
            it.copy(music = value)
        }
    }

    private fun processSetSound(value: Int) = viewModelScope.launch {
        gameRepository.setSound(value)
        _state.update {
            it.copy(sound = value)
        }
    }
}