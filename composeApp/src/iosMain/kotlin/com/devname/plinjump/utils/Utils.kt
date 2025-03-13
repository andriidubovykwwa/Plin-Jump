package com.devname.plinjump.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import platform.UIKit.UIScreen

fun dpToPx(dp: Float): Float {
    val scale = UIScreen.mainScreen.scale.toFloat()
    return dp * scale
}

fun pxToDp(px: Float): Float {
    val scale = UIScreen.mainScreen.scale.toFloat()
    return px / scale
}

fun getCurrentDayOfMonth(): Int {
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return currentDateTime.date.dayOfMonth
}

