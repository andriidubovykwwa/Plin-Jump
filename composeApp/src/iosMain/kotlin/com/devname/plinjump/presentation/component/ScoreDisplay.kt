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
import com.devname.plinjump.utils.scoreDisplayGradientColor1
import com.devname.plinjump.utils.scoreDisplayGradientColor2

@Composable
fun ScoreDisplay(modifier: Modifier = Modifier, score: Int) {
    val shape = RoundedCornerShape(15.dp)
    Box(
        modifier = modifier
            .border(3.dp, Color.White, shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        scoreDisplayGradientColor1,
                        scoreDisplayGradientColor2
                    )
                ),
                shape
            )
            .clip(shape)
            .padding(vertical = 8.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        GameText(text = score.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}