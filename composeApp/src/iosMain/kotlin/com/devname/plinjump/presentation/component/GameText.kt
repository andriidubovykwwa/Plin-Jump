package com.devname.plinjump.presentation.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import plinjump.composeapp.generated.resources.Res
import plinjump.composeapp.generated.resources.kronaone_regular

@Composable
fun GameText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 25.sp,
    fontWeight: FontWeight? = null,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight,
        fontFamily = FontFamily(Font(Res.font.kronaone_regular)),
        textAlign = textAlign
    )
}