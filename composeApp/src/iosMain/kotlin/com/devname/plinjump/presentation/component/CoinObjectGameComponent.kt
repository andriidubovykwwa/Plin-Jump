package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.devname.plinjump.utils.pxToDp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.coin

@Composable
fun CoinObjectGameComponent(
    gameSize: IntSize,
    blockSize: Float,
    coinX: Float
) {
    Image(
        modifier = Modifier.size(pxToDp(blockSize).dp).offset {
            IntOffset(
                x = (gameSize.width * coinX).toInt(),
                y = (gameSize.height - blockSize).toInt()
            )
        }.scale(0.75f),
        painter = painterResource(Res.drawable.coin),
        contentDescription = stringResource(Res.string.coin),
        contentScale = ContentScale.FillBounds
    )
}