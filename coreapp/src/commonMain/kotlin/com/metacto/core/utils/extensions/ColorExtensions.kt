package com.metacto.core.utils.extensions

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

fun Color.blend(background: Color): Color {
    val foregroundAlpha = this.alpha
    val backgroundAlpha = background.alpha

    val newAlpha = foregroundAlpha + backgroundAlpha * (1 - foregroundAlpha)

    // Blend the red, green, and blue channels
    val newRed = (this.red * foregroundAlpha + background.red * backgroundAlpha * (1 - foregroundAlpha)) / newAlpha
    val newGreen = (this.green * foregroundAlpha + background.green * backgroundAlpha * (1 - foregroundAlpha)) / newAlpha
    val newBlue = (this.blue * foregroundAlpha + background.blue * backgroundAlpha * (1 - foregroundAlpha)) / newAlpha

    // Return the new color with full opacity (1.0f for alpha)
    return Color(newRed, newGreen, newBlue, newAlpha)
}

fun String.toColor(): Color {
    val colorString = this.removePrefix("#")
    val colorInt = colorString.toLong(16)

    return if (colorString.length == 8) {
        Color(colorInt)
    } else {
        Color(colorInt or 0xFF000000)
    }
}

fun Color.toHexCode(): String {
    val red = (red * 255).roundToInt()
    val green = (green * 255).roundToInt()
    val blue = (blue * 255).roundToInt()
    val alpha = (alpha * 255).roundToInt()
    return "#%02X%02X%02X%02X".formatNatively(alpha, red, green, blue)
}

fun ULong?.toColor(): Color? {
    return if (this != null) Color(this) else null
}