package com.devname.plinjump.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AdaptiveContainer(
    modifier: Modifier = Modifier,
    space: Dp? = null,
    content: @Composable () -> Unit
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val isPortrait = containerSize.height >= containerSize.width
    if (isPortrait) {
        Column(
            modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (space == null) Arrangement.SpaceEvenly
            else Arrangement.spacedBy(space)
        ) {
            content()
        }
    } else {
        Row(
            modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (space == null) Arrangement.SpaceEvenly
            else Arrangement.spacedBy(space)
        ) {
            content()
        }
    }
}