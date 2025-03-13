package com.devname.plinjump.presentation.screen.menu.view_model

sealed interface MenuEvent {
    data class SetMusic(val value: Int) : MenuEvent
    data class SetSound(val value: Int) : MenuEvent
    data object ProcessDailyDataReset : MenuEvent
    data class ClaimQuest(val index: Int) : MenuEvent
}