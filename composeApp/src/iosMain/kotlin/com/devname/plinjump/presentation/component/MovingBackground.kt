package com.devname.plinjump.presentation.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.background

@Composable
fun MovingBackground(isAnimationRunning: Boolean) {
    var animationRunning by remember { mutableStateOf(isAnimationRunning) }
    var stopOffsetX by remember { mutableStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition()
    val offsetX by infiniteTransition.animateFloat(
        initialValue = if (animationRunning) 0f else stopOffsetX,
        targetValue = if (animationRunning) -1f else stopOffsetX,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    if (isAnimationRunning != animationRunning) {
        animationRunning = isAnimationRunning
        if (!isAnimationRunning) stopOffsetX = offsetX
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationX = offsetX * size.width },
            contentScale = ContentScale.FillBounds,
            painter = painterResource(Res.drawable.background),
            contentDescription = "b"
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationX = (1 + offsetX) * size.width },
            contentScale = ContentScale.FillBounds,
            painter = painterResource(Res.drawable.background),
            contentDescription = "b"
        )
    }
}
