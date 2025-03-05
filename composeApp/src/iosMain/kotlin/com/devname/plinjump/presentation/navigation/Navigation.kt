package com.devname.plinjump.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devname.plinjump.presentation.screen.game.GameScreen
import com.devname.plinjump.presentation.screen.info.InfoScreen
import com.devname.plinjump.presentation.screen.menu.MenuScreen
import com.devname.plinjump.presentation.screen.shop.ShopScreen
import com.devname.plinjump.presentation.screen.skins.SkinsScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.Menu
    ) {
        composable<Screen.Menu> {
            MenuScreen(navController = navController)
        }
        composable<Screen.Game> {
            GameScreen(navController = navController)
        }
        composable<Screen.Info> {
            InfoScreen(navController = navController)
        }
        composable<Screen.Shop> {
            ShopScreen(navController = navController)
        }
        composable<Screen.Skins> {
            SkinsScreen(navController = navController)
        }
    }
}
