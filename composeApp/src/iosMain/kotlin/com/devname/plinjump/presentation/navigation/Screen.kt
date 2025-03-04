package com.devname.plinjump.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Menu : Screen

    @Serializable
    data object Game : Screen
}