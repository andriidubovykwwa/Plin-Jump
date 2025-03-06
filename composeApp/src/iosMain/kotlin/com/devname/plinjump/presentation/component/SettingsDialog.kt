package com.devname.plinjump.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.music
import plinjump.composeapp.generated.resources.settings
import plinjump.composeapp.generated.resources.sounds

@Composable
fun SettingsDialog(
    modifier: Modifier = Modifier,
    music: Int,
    sound: Int,
    onSetMusic: (Int) -> Unit,
    onSetSound: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        val dialogShape = RoundedCornerShape(15.dp)
        Column(
            modifier.background(Color(0x99000000), dialogShape)
                .border(2.dp, Color.White, dialogShape).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GameText(
                text = stringResource(Res.string.settings),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            GameText(
                text = stringResource(Res.string.music),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = music.toFloat(),
                onValueChange = { onSetMusic(it.toInt()) },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xff21CF46),
                    activeTrackColor = Color(0xff21CF46),
                    inactiveTrackColor = Color(0xff282844),
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                ),
            )
            Spacer(Modifier.height(5.dp))
            GameText(
                text = stringResource(Res.string.sounds),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = sound.toFloat(),
                onValueChange = { onSetSound(it.toInt()) },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xff21CF46),
                    activeTrackColor = Color(0xff21CF46),
                    inactiveTrackColor = Color(0xff282844),
                    activeTickColor = Color.Transparent,
                    inactiveTickColor = Color.Transparent
                ),
            )
        }
    }
}