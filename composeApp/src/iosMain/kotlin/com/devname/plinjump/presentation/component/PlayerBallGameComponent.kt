package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.pxToDp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.ball

@Composable
fun PlayerBallGameComponent(
    gameSize: IntSize,
    blockSize: Float,
    playerY: Float,
    selectedSkinIndex: Int
) {
    Image(
        modifier = Modifier.size(pxToDp(blockSize).dp).offset {
            IntOffset(
                x = ((gameSize.width - blockSize) / 2).toInt(),
                y = (gameSize.height - blockSize - playerY * gameSize.height).toInt()
            )
        },
        painter = painterResource(GameConfig.Skin.entries[selectedSkinIndex].res),
        contentDescription = stringResource(Res.string.ball)
    )
}