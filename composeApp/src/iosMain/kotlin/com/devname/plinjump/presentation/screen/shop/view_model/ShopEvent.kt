package com.devname.plinjump.presentation.screen.shop.view_model

sealed interface ShopEvent {
    data object BuyShield : ShopEvent
    data object BuyFireball : ShopEvent
}