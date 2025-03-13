package com.devname.plinjump.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBar(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(10.dp),
    currentProgress: Int,
    targetProgress: Int,
) {
    val progress = (currentProgress / targetProgress.toFloat()).coerceIn(0f, 1f)
    val shape = RoundedCornerShape(4.dp)
    Box(
        modifier = modifier
            .background(Color(0xffa3b8ac), shape = shape)
            .clip(shape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(Color(0xff17914a), shape = RoundedCornerShape(4.dp))
        )
    }
}