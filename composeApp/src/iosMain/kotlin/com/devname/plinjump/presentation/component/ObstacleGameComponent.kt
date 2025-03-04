package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.devname.plinjump.utils.pxToDp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.obstacle
import plinjump.composeapp.generated.resources.obstacle_block

@Composable
fun ObstacleGameComponent(
    gameSize: IntSize,
    blockSize: Float,
    obstacleX: Float
) {
    Image(
        modifier = Modifier.size(pxToDp(blockSize).dp).offset {
            IntOffset(
                x = (gameSize.width * obstacleX).toInt(),
                y = (gameSize.height - blockSize).toInt()
            )
        },
        painter = painterResource(Res.drawable.obstacle_block),
        contentDescription = stringResource(Res.string.obstacle)
    )
}