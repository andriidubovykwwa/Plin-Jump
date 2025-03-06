package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
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
import plinjump.composeapp.generated.resources.ball
import plinjump.composeapp.generated.resources.buy
import plinjump.composeapp.generated.resources.coin
import plinjump.composeapp.generated.resources.select
import plinjump.composeapp.generated.resources.selected

@Composable
fun SkinComponent(
    modifier: Modifier = Modifier,
    price: Int,
    coins: Int,
    index: Int,
    onBuy: () -> Unit,
    onSelect: () -> Unit,
    isSelected: Boolean,
    isAvailable: Boolean,
    res: DrawableResource
) {
    val shape = RoundedCornerShape(20.dp)
    Row(
        modifier.border(5.dp, if (isSelected) Color(0xff4e4eca) else Color(0xffb03dd9), shape)
            .background(
                if (isSelected) {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xff0a0a75),
                            Color(0xff3838a8),
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xff411F98),
                            Color(0xff842AA2),
                        )
                    )
                },
                shape
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(res),
                contentScale = ContentScale.FillBounds,
                contentDescription = "${stringResource(Res.string.ball)} $index",
            )
        }
        Column(
            Modifier.weight(2f).padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                Modifier.heightIn(min = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                if (!isAvailable) {
                    GameText(
                        text = price.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.coin),
                        contentDescription = stringResource(Res.string.coin),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            val brush = if (coins >= price || isAvailable) {
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
            val borderColor = if (coins >= price || isAvailable) Color(0xff55cb4f)
            else Color(0xffea191a)
            Box(
                Modifier.border(3.dp, borderColor, shape)
                    .background(
                        brush,
                        shape
                    )
                    .clip(shape)
                    .clickable {
                        if (isAvailable) onSelect()
                        else onBuy()
                    }
                    .padding(vertical = 5.dp, horizontal = 30.dp),
            ) {
                GameText(
                    text = if (isSelected) stringResource(Res.string.selected)
                    else if (isAvailable) stringResource(Res.string.select)
                    else stringResource(Res.string.buy),
                    fontSize = 19.sp
                )
            }
        }
    }
}