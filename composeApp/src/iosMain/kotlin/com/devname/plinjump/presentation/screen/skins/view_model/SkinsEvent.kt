package com.devname.plinjump.presentation.screen.skins.view_model

sealed interface SkinsEvent {
    data class BuySkin(val index: Int) : SkinsEvent
    data class SelectSkin(val index: Int) : SkinsEvent
}