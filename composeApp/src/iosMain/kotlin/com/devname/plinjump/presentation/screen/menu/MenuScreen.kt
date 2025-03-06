package com.devname.plinjump.presentation.screen.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.AdaptiveContainer
import com.devname.plinjump.presentation.component.GameText
import com.devname.plinjump.presentation.navigation.Screen
import com.devname.plinjump.presentation.screen.SharedData
import com.devname.plinjump.presentation.screen.menu.view_model.MenuViewModel
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.OrientationManager
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.app_name
import plinjump.composeapp.generated.resources.app_title
import plinjump.composeapp.generated.resources.ball
import plinjump.composeapp.generated.resources.bg_menu
import plinjump.composeapp.generated.resources.info
import plinjump.composeapp.generated.resources.info_button
import plinjump.composeapp.generated.resources.play_button
import plinjump.composeapp.generated.resources.round_button_bg
import plinjump.composeapp.generated.resources.settings
import plinjump.composeapp.generated.resources.settings_button
import plinjump.composeapp.generated.resources.shop
import plinjump.composeapp.generated.resources.shop_button
import plinjump.composeapp.generated.resources.start

@Composable
fun MenuScreen(navController: NavController, viewModel: MenuViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val sharedHighScore by SharedData.highScore.collectAsState(null)
    val sharedSelectedSkinIndex by SharedData.selectedSkinIndex.collectAsState(null)
    LaunchedEffect(Unit) {
        OrientationManager().orientation = OrientationManager.Orientation.ALL
    }
    Box(
        Modifier.fillMaxSize().paint(
            painter = painterResource(Res.drawable.bg_menu),
            contentScale = ContentScale.Crop
        ).safeContentPadding(), contentAlignment = Alignment.Center
    ) {
        AdaptiveContainer(Modifier.fillMaxSize().align(Alignment.Center)) {
            Image(
                modifier = Modifier.height(200.dp),
                painter = painterResource(Res.drawable.app_title),
                contentDescription = stringResource(Res.string.app_name),
                contentScale = ContentScale.FillHeight,
            )
            Image(
                modifier = Modifier.size(150.dp).clip(CircleShape).clickable {
                    navController.navigate(Screen.Game)
                },
                painter = painterResource(Res.drawable.play_button),
                contentDescription = stringResource(Res.string.start),
                contentScale = ContentScale.FillBounds,
            )
            val shape = RoundedCornerShape(10.dp)
            Column(
                modifier = Modifier.background(Color(0xff441768), shape)
                    .border(3.dp, Color.White, shape)
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                GameText(text = "High Score", textAlign = TextAlign.Center, fontSize = 20.sp)
                GameText(text = "${(sharedHighScore ?: state.highScore)}", fontSize = 20.sp)
            }
        }
        AdaptiveContainer(
            Modifier.align(Alignment.TopEnd),
            space = 5.dp
        ) {
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape).clickable {
                    // TODO: open settings
                },
                painter = painterResource(Res.drawable.settings_button),
                contentDescription = stringResource(Res.string.settings),
                contentScale = ContentScale.FillBounds,
            )
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape).clickable {
                    navController.navigate(Screen.Info)
                },
                painter = painterResource(Res.drawable.info_button),
                contentDescription = stringResource(Res.string.info),
                contentScale = ContentScale.FillBounds,
            )
            Image(
                modifier = Modifier.size(40.dp).clip(CircleShape).clickable {
                    navController.navigate(Screen.Shop)
                },
                painter = painterResource(Res.drawable.shop_button),
                contentDescription = stringResource(Res.string.shop),
                contentScale = ContentScale.FillBounds,
            )
            Box(
                modifier = Modifier.size(40.dp).paint(
                    painter = painterResource(Res.drawable.round_button_bg),
                    contentScale = ContentScale.FillBounds
                ).clip(CircleShape).clickable {
                    navController.navigate(Screen.Skins)
                },
                contentAlignment = Alignment.Center
            ) {
                val skinIndex = sharedSelectedSkinIndex ?: state.selectedSkinIndex
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(GameConfig.Skin.entries[skinIndex].res),
                    contentDescription = stringResource(Res.string.ball),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}