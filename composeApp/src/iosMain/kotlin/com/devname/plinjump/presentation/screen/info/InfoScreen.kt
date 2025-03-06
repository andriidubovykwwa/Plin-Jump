package com.devname.plinjump.presentation.screen.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devname.plinjump.presentation.component.GameText
import com.devname.plinjump.presentation.screen.info.view_model.InfoViewModel
import com.devname.plinjump.utils.SoundManager
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.back
import plinjump.composeapp.generated.resources.bg_info
import plinjump.composeapp.generated.resources.fireball
import plinjump.composeapp.generated.resources.icon_back
import plinjump.composeapp.generated.resources.info
import plinjump.composeapp.generated.resources.shield

@Composable
fun InfoScreen(navController: NavController, viewModel: InfoViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.bg_info),
                contentScale = ContentScale.Crop
            )
    ) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = WindowInsets.safeContent.asPaddingValues(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.width(32.dp).clickable {
                            SoundManager.playButtonClick(state.sound)
                            navController.popBackStack()
                        },
                        painter = painterResource(Res.drawable.icon_back),
                        contentDescription = stringResource(Res.string.back),
                        contentScale = ContentScale.FillWidth,
                    )
                    GameText(text = stringResource(Res.string.info), fontSize = 30.sp)
                    Spacer(Modifier.width(32.dp))
                }
            }
            item {
                GameText(
                    text = "Your goal is to jump over obstacles and collect coins. As the game progresses, the ball’s speed increases, making it harder to avoid obstacles.",
                    fontSize = 18.sp
                )
            }
            item {
                GameText(
                    text = "Collected coins can be spent on one-time power-ups or ball skins. There are two types of power-ups: Shield and Fireball.",
                    fontSize = 18.sp
                )
            }
            item {
                Image(
                    modifier = Modifier.size(75.dp),
                    painter = painterResource(Res.drawable.shield),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = stringResource(Res.string.shield),
                )
            }
            item {
                GameText(
                    text = "Shield - protects the ball from collisions but breaks upon the first impact.",
                    fontSize = 18.sp
                )
            }
            item {
                Image(
                    modifier = Modifier.size(75.dp),
                    painter = painterResource(Res.drawable.fireball),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = stringResource(Res.string.fireball),
                )
            }
            item {
                GameText(
                    text = "Fireball - allows the ball to destroy all obstacles for a limited time",
                    fontSize = 18.sp
                )
            }
            item {
                GameText(
                    text = "Time your jumps carefully, use power-ups wisely, and see how far you can go in this exciting challenge!",
                    fontSize = 18.sp
                )
            }
        }
    }
}