package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.devname.plinjump.utils.availableToBuyGradientColor1
import com.devname.plinjump.utils.availableToBuyGradientColor2
import com.devname.plinjump.utils.shopCardColor
import com.devname.plinjump.utils.shopCardPriceColor
import com.devname.plinjump.utils.unavailableToBuyGradientColor1
import com.devname.plinjump.utils.unavailableToBuyGradientColor2
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
    val brush = if (coins >= price) {
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
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
                .border(4.dp, Color.White, shape)
                .background(shopCardColor, shape).clip(shape),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                Modifier.fillMaxWidth().background(shopCardPriceColor).padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                GameText(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.height(90.dp),
                    painter = painterResource(res),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = name,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
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
                    Box(
                        Modifier.widthIn(min = 100.dp).border(3.dp, Color.White, shape)
                            .background(
                                brush,
                                shape
                            )
                            .clip(shape)
                            .clickable {
                                if (coins >= price) onBuy()
                            }
                            .padding(vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        GameText(
                            text = stringResource(Res.string.buy),
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}