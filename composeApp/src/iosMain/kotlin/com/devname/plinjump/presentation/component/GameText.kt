package com.devname.plinjump.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.londrinasolide_regular

@Composable
fun GameText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 25.sp,
    fontWeight: FontWeight? = null,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
) {
    Box(modifier) {
        @OptIn(ExperimentalComposeUiApi::class)
        Text(
            text = text,
            modifier = Modifier.semantics { invisibleToUser() },
            color = Color.Black,
            style = LocalTextStyle.current.copy(drawStyle = Stroke(8f)),
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = FontFamily(Font(Res.font.londrinasolide_regular)),
            textAlign = textAlign
        )

        Text(
            modifier = modifier,
            text = text,
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight,
            fontFamily = FontFamily(Font(Res.font.londrinasolide_regular)),
            textAlign = textAlign
        )
    }
}