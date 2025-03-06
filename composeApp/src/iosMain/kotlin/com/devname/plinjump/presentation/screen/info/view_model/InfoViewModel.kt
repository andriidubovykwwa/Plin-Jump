package com.devname.plinjump.presentation.screen.info.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.domain.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(InfoState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(sound = gameRepository.getSound()) }
        }
    }
}