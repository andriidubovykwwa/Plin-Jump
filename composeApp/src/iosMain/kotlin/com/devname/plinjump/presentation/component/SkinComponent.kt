package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.devname.plinjump.utils.availableToBuyGradientColor1
import com.devname.plinjump.utils.availableToBuyGradientColor2
import com.devname.plinjump.utils.selectGradientColor1
import com.devname.plinjump.utils.selectGradientColor2
import com.devname.plinjump.utils.selectedGradientColor1
import com.devname.plinjump.utils.selectedGradientColor2
import com.devname.plinjump.utils.shopCardColor
import com.devname.plinjump.utils.shopCardPriceColor
import com.devname.plinjump.utils.unavailableToBuyGradientColor1
import com.devname.plinjump.utils.unavailableToBuyGradientColor2
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.available
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
    val brush = if (isSelected) {
        Brush.verticalGradient(
            colors = listOf(
                selectedGradientColor1,
                selectedGradientColor2
            )
        )
    } else if (isAvailable) {
        Brush.verticalGradient(
            colors = listOf(
                selectGradientColor1,
                selectGradientColor2
            )
        )
    } else if (coins >= price) {
        Brush.verticalGradient(
            colors = listOf(
                availableToBuyGradientColor1,
                availableToBuyGradientColor2
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                unavailableToBuyGradientColor1,
                unavailableToBuyGradientColor2
            )
        )
    }
    Column(
        modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            Modifier.width(IntrinsicSize.Min)
                .border(4.dp, if (isSelected) selectedGradientColor1 else Color.White, shape)
                .background(shopCardColor, shape).clip(shape),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                Modifier.fillMaxWidth().background(shopCardPriceColor).padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isAvailable) {
                    GameText(
                        text = stringResource(Res.string.available),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    GameText(
                        text = price.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(5.dp))
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(Res.drawable.coin),
                        contentDescription = stringResource(Res.string.coin),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Box(
                Modifier.padding(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(90.dp),
                    painter = painterResource(res),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "${stringResource(Res.string.ball)} $index",
                )
            }
        }
        Box(
            Modifier.fillMaxWidth().border(3.dp, Color.White, shape)
                .background(
                    brush,
                    shape
                )
                .clip(shape)
                .clickable {
                    if (isAvailable) onSelect()
                    else onBuy()
                }
                .padding(vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            GameText(
                text = if (isSelected) stringResource(Res.string.selected)
                else if (isAvailable) stringResource(Res.string.select)
                else stringResource(Res.string.buy),
                fontSize = 24.sp
            )
        }
    }

}