package com.devname.plinjump.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Menu : Screen

    @Serializable
    data object Game : Screen

    @Serializable
    data object Info : Screen

    @Serializable
    data object Shop : Screen

    @Serializable
    data object Skins : Screen
}