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
import plinjump.composeapp.generated.resources.home
import plinjump.composeapp.generated.resources.icon_home
import plinjump.composeapp.generated.resources.icon_restart
import plinjump.composeapp.generated.resources.pause
import plinjump.composeapp.generated.resources.restart

@Composable
fun PauseDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onBackHome: () -> Unit,
    onRestart: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
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
                text = stringResource(Res.string.pause),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
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