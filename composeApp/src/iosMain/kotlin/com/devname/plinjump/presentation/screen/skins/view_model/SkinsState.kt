package com.devname.plinjump.presentation.screen.skins.view_model

import com.devname.plinjump.utils.GameConfig

data class SkinsState(
    val coins: Int = 0,
    val skinsStatuses: List<Boolean> = List(GameConfig.Skin.entries.size) { it == 0 },
    val selectedSkinIndex: Int = 0,
    val sound: Int = 5
)
