package com.devname.plinjump.presentation.screen.skins.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.domain.GameRepository
import com.devname.plinjump.utils.GameConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SkinsViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SkinsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    coins = gameRepository.getCoins(),
                    skinsStatuses = gameRepository.getSkinStatuses(),
                    selectedSkinIndex = gameRepository.getSelectedSkin(),
                    sound = gameRepository.getSound()
                )
            }
        }
    }

    fun onEvent(event: SkinsEvent) {
        when (event) {
            is SkinsEvent.BuySkin -> processBuySkin(event.index)
            is SkinsEvent.SelectSkin -> processSelectSkin(event.index)
        }
    }

    private fun processBuySkin(index: Int) = viewModelScope.launch {
        if (index < 0 && index >= GameConfig.Skin.entries.size) return@launch
        val skin = GameConfig.Skin.entries[index]
        if (state.value.coins < skin.price) return@launch
        if (state.value.skinsStatuses[index]) return@launch
        val newCoins = state.value.coins - skin.price
        val newSkinStatuses = state.value.skinsStatuses.mapIndexed { i, it ->
            if (i == index) true else it
        }
        gameRepository.activateSkin(index)
        _state.update {
            it.copy(coins = newCoins, skinsStatuses = newSkinStatuses)
        }
    }

    private fun processSelectSkin(index: Int) = viewModelScope.launch {
        if (index < 0 && index >= GameConfig.Skin.entries.size) return@launch
        if (!state.value.skinsStatuses[index]) return@launch
        gameRepository.setSelectedSkin(index)
        _state.update {
            it.copy(selectedSkinIndex = index)
        }
    }
}