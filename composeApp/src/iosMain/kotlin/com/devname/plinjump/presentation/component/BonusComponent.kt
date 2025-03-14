package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devname.plinjump.utils.selectGradientColor1
import com.devname.plinjump.utils.shopCardColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.fireball
import plinjump.composeapp.generated.resources.shield

@Composable
fun BonusComponent(
    modifier: Modifier = Modifier,
    onActivateShield: () -> Unit = {},
    onActivateFireball: () -> Unit = {},
    shieldSeconds: Int = 0,
    fireballSeconds: Int = 0,
    shields: Int,
    fireballs: Int,
    imgHeight: Dp = 70.dp,
    alwaysDisplayAll: Boolean = false
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shape = RoundedCornerShape(15.dp)
        if (shieldSeconds > 0 || fireballSeconds > 0) {
            Box(
                modifier = modifier
                    .widthIn(min = 125.dp)
                    .border(4.dp, Color.White, shape)
                    .background(selectGradientColor1, shape)
                    .clip(shape)
                    .clickable { onActivateShield() }
                    .padding(8.dp),
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center).height(imgHeight),
                    painter = painterResource(if (shieldSeconds > 0) Res.drawable.shield else Res.drawable.fireball),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = stringResource(if (shieldSeconds > 0) Res.string.shield else Res.string.fireball)
                )
                GameText(
                    Modifier.align(Alignment.BottomEnd),
                    text = "${if (shieldSeconds > 0) shieldSeconds else fireballSeconds}s",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            if (shields > 0 || alwaysDisplayAll) {
                Box(
                    modifier = modifier
                        .widthIn(min = 125.dp)
                        .border(4.dp, Color.White, shape)
                        .background(shopCardColor, shape)
                        .clip(shape)
                        .clickable { onActivateShield() }
                        .padding(8.dp),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center).height(imgHeight),
                        painter = painterResource(Res.drawable.shield),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = stringResource(Res.string.shield)
                    )
                    GameText(
                        Modifier.align(Alignment.BottomEnd),
                        text = shields.toString(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (fireballs > 0 || alwaysDisplayAll) {
                Box(
                    modifier = modifier
                        .widthIn(min = 125.dp)
                        .border(4.dp, Color.White, shape)
                        .background(shopCardColor, shape)
                        .clip(shape)
                        .clickable { onActivateFireball() }
                        .padding(8.dp),
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center).height(imgHeight),
                        painter = painterResource(Res.drawable.fireball),
                        contentScale = ContentScale.FillHeight,
                        contentDescription = stringResource(Res.string.fireball)
                    )
                    GameText(
                        Modifier.align(Alignment.BottomEnd),
                        text = fireballs.toString(),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}