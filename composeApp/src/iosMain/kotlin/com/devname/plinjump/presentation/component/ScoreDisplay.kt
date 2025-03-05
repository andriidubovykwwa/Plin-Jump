package com.devname.plinjump.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreDisplay(modifier: Modifier = Modifier, score: Int) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .border(3.dp, Color(0xffc250c8), shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff740A65),
                        Color(0xffA737A7),
                    )
                ),
                shape
            )
            .clip(shape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        GameText(text = score.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}