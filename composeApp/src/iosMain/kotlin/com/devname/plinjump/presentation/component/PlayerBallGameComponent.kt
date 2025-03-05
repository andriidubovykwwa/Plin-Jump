package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.devname.plinjump.utils.GameConfig
import com.devname.plinjump.utils.pxToDp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.ball
import plinjump.composeapp.generated.resources.fireball

@Composable
fun PlayerBallGameComponent(
    gameSize: IntSize,
    blockSize: Float,
    playerY: Float,
    selectedSkinIndex: Int,
    rotation: Float,
    isShieldActive: Boolean,
    isFireballActive: Boolean
) {
    Image(
        modifier = Modifier
            .size(pxToDp(blockSize).dp)
            .offset {
                IntOffset(
                    x = ((gameSize.width - blockSize) / 2).toInt(),
                    y = (gameSize.height - blockSize - playerY * gameSize.height).toInt()
                )
            }
            .then(
                if (!isShieldActive) Modifier
                else Modifier.border(5.dp, Color(0xaa7DCFFF), CircleShape)
            )
            .rotate(rotation),
        painter = painterResource(
            if (!isFireballActive) GameConfig.Skin.entries[selectedSkinIndex].res
            else Res.drawable.fireball
        ),
        contentDescription = stringResource(Res.string.ball)
    )
}