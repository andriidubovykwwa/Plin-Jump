package com.devname.plinjump.presentation.screen.menu.view_model

data class MenuState(
    val highScore: Int = 0,
    val selectedSkinIndex: Int = 0,
    val music: Int = 5,
    val sound: Int = 5,
    val playedDailyGames: Int = 0,
    val dailyRecord: Int = 0,
    val claimedQuests: List<Int> = emptyList()
)
