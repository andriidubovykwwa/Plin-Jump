package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.coin

@Composable
fun CoinDisplay(modifier: Modifier = Modifier, coins: Int) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .border(3.dp, Color(0xff61add0), shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff0A5A74),
                        Color(0xff3777A7),
                    )
                ),
                shape
            )
            .clip(shape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            GameText(text = coins.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(Res.drawable.coin),
                contentDescription = stringResource(Res.string.coin),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}