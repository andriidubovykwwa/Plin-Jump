package com.devname.plinjump.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.window.Dialog
import com.devname.plinjump.utils.dialogGradientColor1
import com.devname.plinjump.utils.dialogGradientColor2
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.game_over
import plinjump.composeapp.generated.resources.home
import plinjump.composeapp.generated.resources.icon_home
import plinjump.composeapp.generated.resources.icon_restart
import plinjump.composeapp.generated.resources.restart

@Composable
fun GameOverDialog(
    modifier: Modifier = Modifier,
    onBackHome: () -> Unit,
    onRestart: () -> Unit,
    score: Int,
    coins: Int
) {
    Dialog(onDismissRequest = {}) {
        val dialogShape = RoundedCornerShape(15.dp)
        Column(
            modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        dialogGradientColor1,
                        dialogGradientColor2,
                    )
                ), dialogShape
            )
                .border(2.dp, Color.White, dialogShape).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GameText(
                text = stringResource(Res.string.game_over),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            ScoreDisplay(Modifier.widthIn(min = 80.dp), score = score)
            Spacer(Modifier.height(4.dp))
            CoinDisplay(Modifier.widthIn(min = 80.dp), coins = coins)
            Spacer(Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                val shape = RoundedCornerShape(18.dp)
                Image(
                    modifier = Modifier.size(60.dp).clip(shape)
                        .clickable { onBackHome() },
                    painter = painterResource(Res.drawable.icon_home),
                    contentDescription = stringResource(Res.string.home),
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    modifier = Modifier.size(60.dp).clip(shape)
                        .clickable { onRestart() },
                    painter = painterResource(Res.drawable.icon_restart),
                    contentDescription = stringResource(Res.string.restart),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}