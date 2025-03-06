package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.buy
import plinjump.composeapp.generated.resources.coin

@Composable
fun ShopComponent(
    modifier: Modifier = Modifier,
    name: String,
    price: Int,
    coins: Int,
    onBuy: () -> Unit,
    res: DrawableResource
) {
    val shape = RoundedCornerShape(20.dp)
    Row(
        modifier.border(5.dp, Color(0xffb03dd9), shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff411F98),
                        Color(0xff842AA2),
                    )
                ),
                shape
            )
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(res),
                contentScale = ContentScale.FillBounds,
                contentDescription = name,
            )
        }
        Column(
            Modifier.weight(2f).padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GameText(text = name, fontSize = 22.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                GameText(text = price.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(Res.drawable.coin),
                    contentDescription = stringResource(Res.string.coin),
                    contentScale = ContentScale.FillBounds
                )
            }
            val brush = if (coins >= price) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff0A743E),
                        Color(0xff3DA737),
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff740A0A),
                        Color(0xffA73737),
                    )
                )
            }
            val borderColor = if (coins >= price) Color(0xff55cb4f)
            else Color(0xffea191a)
            Box(
                Modifier.border(3.dp, borderColor, shape)
                    .background(
                        brush,
                        shape
                    )
                    .padding(vertical = 5.dp, horizontal = 30.dp)
                    .clip(shape)
                    .clickable { onBuy() },
            ) {
                GameText(text = stringResource(Res.string.buy), fontSize = 19.sp)
            }
        }
    }
}