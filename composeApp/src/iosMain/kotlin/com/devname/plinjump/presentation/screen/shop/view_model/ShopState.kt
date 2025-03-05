package com.devname.plinjump.presentation.screen.shop.view_model

import com.devname.plinjump.utils.GameConfig

data class ShopState(
    val coins: Int = 0,
    val shields: Int = 0,
    val fireballs: Int = 0
) {
    val canBuyShield: Boolean
        get() = coins >= GameConfig.SHIELD_PRICE

    val canBuyFireball: Boolean
        get() = coins >= GameConfig.FIREBALL_PRICE
}
