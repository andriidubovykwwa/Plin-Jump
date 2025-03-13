package com.devname.plinjump.presentation.screen.menu.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devname.plinjump.data.GameRepositoryImpl
import com.devname.plinjump.domain.GameRepository
import com.devname.plinjump.utils.GameConfig
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
                val claimedQuests = mutableListOf<Int>()
                repeat(GameRepositoryImpl.NUMBER_OF_DAILY_REWARDS) { index ->
                    val isReceived = gameRepository.isRewardReceived(index)
                    if (isReceived) claimedQuests.add(index)
                }
                it.copy(
                    highScore = gameRepository.getHighScore(),
                    selectedSkinIndex = gameRepository.getSelectedSkin(),
                    music = gameRepository.getMusic(),
                    sound = gameRepository.getSound(),
                    claimedQuests = claimedQuests
                )
            }
        }
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SetMusic -> processSetMusic(event.value)
            is MenuEvent.SetSound -> processSetSound(event.value)
            is MenuEvent.ProcessDailyDataReset -> processDailyDataReset()
            is MenuEvent.ClaimQuest -> processClaimQuest(event.index)
        }
    }

    private fun processClaimQuest(index: Int) = viewModelScope.launch {
        if (state.value.claimedQuests.contains(index)) return@launch
        val reward = when (index) {
            0 -> GameConfig.QUEST_1_REWARD
            1 -> GameConfig.QUEST_2_REWARD
            2 -> GameConfig.QUEST_3_REWARD
            else -> 0
        }
        gameRepository.setCoins(gameRepository.getCoins() + reward)
        gameRepository.setRewardReceived(index)
        _state.update {
            it.copy(
                claimedQuests = it.claimedQuests + index
            )
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

    private fun processDailyDataReset() = viewModelScope.launch {
        gameRepository.processDailyDataReset()
        _state.update {
            it.copy(
                dailyRecord = gameRepository.getDailyRecord(),
                playedDailyGames = gameRepository.getDailyPlayedGames()
            )
        }
    }
}