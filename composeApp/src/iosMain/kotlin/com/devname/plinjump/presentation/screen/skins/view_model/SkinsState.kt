package com.devname.plinjump.presentation.screen.skins.view_model

import com.devname.plinjump.utils.GameConfig

data class SkinsState(
    val coins: Int = 0,
    val skinsStatuses: List<Boolean> = List(GameConfig.Skin.entries.size) { it == 0 },
    val selectedSkinIndex: Int = 0
) {
    fun canBuySkin(index: Int): Boolean {
        return coins >= GameConfig.Skin.entries[index].price
    }
}
