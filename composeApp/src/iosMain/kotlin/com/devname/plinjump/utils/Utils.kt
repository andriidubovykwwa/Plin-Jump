package com.devname.plinjump.utils

import platform.UIKit.UIScreen

fun dpToPx(dp: Float): Float {
    val scale = UIScreen.mainScreen.scale.toFloat()
    return dp * scale
}

fun pxToDp(px: Float): Float {
    val scale = UIScreen.mainScreen.scale.toFloat()
    return px / scale
}

