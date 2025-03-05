package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.fireball
import plinjump.composeapp.generated.resources.shield

@Composable
fun BonusComponent(
    modifier: Modifier = Modifier,
    onActivateShield: () -> Unit,
    onActivateFireball: () -> Unit,
    shieldSeconds: Int,
    fireballSeconds: Int,
    shields: Int,
    fireballs: Int
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val shape = RoundedCornerShape(15.dp)
        if (shieldSeconds > 0 || fireballSeconds > 0) {
            Row(
                modifier = modifier
                    .widthIn(min = 135.dp)
                    .border(5.dp, Color(0xffb6c753), shape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xff88981F),
                                Color(0xff74A22A),
                            )
                        ),
                        shape
                    )
                    .clip(shape)
                    .clickable { onActivateShield() }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(if (shieldSeconds > 0) Res.drawable.shield else Res.drawable.fireball),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = stringResource(if (shieldSeconds > 0) Res.string.shield else Res.string.fireball)
                )
                Spacer(Modifier.width(8.dp))
                GameText(
                    text = "${if (shieldSeconds > 0) shieldSeconds else fireballSeconds}s",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            if (shields > 0) {
                Row(
                    modifier = modifier
                        .widthIn(min = 90.dp)
                        .border(5.dp, Color(0xffc250c8), shape)
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
                        .clickable { onActivateShield() }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(Res.drawable.shield),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = stringResource(Res.string.shield)
                    )
                    Spacer(Modifier.width(8.dp))
                    GameText(
                        text = shields.toString(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (fireballs > 0) {
                Row(
                    modifier = modifier
                        .widthIn(min = 90.dp)
                        .border(5.dp, Color(0xffc250c8), shape)
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
                        .clickable { onActivateFireball() }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(Res.drawable.fireball),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = stringResource(Res.string.fireball)
                    )
                    Spacer(Modifier.width(8.dp))
                    GameText(
                        text = fireballs.toString(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}