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
import com.devname.plinjump.utils.availableToBuyGradientColor1
import com.devname.plinjump.utils.availableToBuyGradientColor2
import com.devname.plinjump.utils.selectGradientColor1
import com.devname.plinjump.utils.selectGradientColor2
import com.devname.plinjump.utils.shopCardColor
import com.devname.plinjump.utils.unavailableToBuyGradientColor1
import com.devname.plinjump.utils.unavailableToBuyGradientColor2
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.claim
import plinjump.composeapp.generated.resources.claimed
import plinjump.composeapp.generated.resources.coin

@Composable
fun DailyQuestComponent(
    modifier: Modifier = Modifier,
    description: String,
    reward: Int,
    onClaim: () -> Unit,
    claimed: Boolean,
    targetProgress: Int,
    currentProgress: Int
) {
    val shape = RoundedCornerShape(20.dp)
    Row(
        modifier.border(4.dp, Color.White, shape)
            .background(
                shopCardColor,
                shape
            )
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.weight(2f).padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GameText(text = description, fontSize = 22.sp)
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.weight(1f).padding(horizontal = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CustomProgressBar(
                        currentProgress = currentProgress,
                        targetProgress = targetProgress
                    )
                    GameText(
                        text = "${minOf(currentProgress, targetProgress)}/$targetProgress",
                        fontSize = 14.sp
                    )
                }
                Row(
                    Modifier.padding(horizontal = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    GameText(
                        text = reward.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(Res.drawable.coin),
                        contentDescription = stringResource(Res.string.coin),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            val brush = if (claimed) {
                Brush.verticalGradient(
                    colors = listOf(
                        selectGradientColor1,
                        selectGradientColor2,
                    )
                )
            } else if (currentProgress >= targetProgress) {
                Brush.verticalGradient(
                    colors = listOf(
                        availableToBuyGradientColor1,
                        availableToBuyGradientColor2,
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
            val borderColor = Color.White
            Box(
                Modifier.border(3.dp, borderColor, shape)
                    .background(
                        brush,
                        shape
                    )
                    .clip(shape)
                    .clickable { onClaim() }
                    .padding(vertical = 5.dp, horizontal = 30.dp),
            ) {
                GameText(
                    text = stringResource(if (claimed) Res.string.claimed else Res.string.claim),
                    fontSize = 19.sp
                )
            }
        }
    }
}