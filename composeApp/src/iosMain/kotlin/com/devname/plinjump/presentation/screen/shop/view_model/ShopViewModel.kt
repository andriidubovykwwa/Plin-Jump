package com.devname.plinjump.presentation.screen.shop.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.domain.GameRepository
import com.devname.plinjump.utils.GameConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ShopState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    coins = gameRepository.getCoins(),
                    shields = gameRepository.getShields(),
                    fireballs = gameRepository.getFireballs()
                )
            }
        }
    }

    fun onEvent(event: ShopEvent) {
        when (event) {
            is ShopEvent.BuyFireball -> processBuyFireball()
            is ShopEvent.BuyShield -> processBuyShield()
        }
    }

    private fun processBuyFireball() = viewModelScope.launch {
        if (!state.value.canBuyShield) return@launch
        val newCoins = state.value.coins - GameConfig.SHIELD_PRICE
        val newShields = state.value.shields + 1
        gameRepository.setCoins(newCoins)
        gameRepository.setShields(newShields)
        _state.update {
            it.copy(coins = newCoins, shields = newShields)
        }
    }

    private fun processBuyShield() = viewModelScope.launch {
        if (!state.value.canBuyFireball) return@launch
        val newCoins = state.value.coins - GameConfig.FIREBALL_PRICE
        val newFireballs = state.value.fireballs + 1
        gameRepository.setCoins(newCoins)
        gameRepository.setFireballs(newFireballs)
        _state.update {
            it.copy(coins = newCoins, fireballs = newFireballs)
        }
    }
}