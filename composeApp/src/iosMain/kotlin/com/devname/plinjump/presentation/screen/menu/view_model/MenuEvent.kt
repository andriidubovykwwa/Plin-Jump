package com.devname.plinjump.presentation.screen.menu.view_model

sealed interface MenuEvent {
    data class SetMusic(val value: Int) : MenuEvent
    data class SetSound(val value: Int) : MenuEvent
}