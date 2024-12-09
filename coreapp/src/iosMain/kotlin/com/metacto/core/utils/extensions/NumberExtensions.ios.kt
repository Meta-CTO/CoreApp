package com.metacto.core.utils.extensions

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSString
import platform.Foundation.currentLocale
import platform.Foundation.stringWithFormat
import platform.UIKit.UIColor

actual fun Double.format(decimalsCount: Int): String {
    return this.truncate(decimalsCount).let {
        NSString.stringWithFormat("%.${decimalsCount}f", it)
    }
}

actual fun Double.formatToComma(maxFractionCount: Int): String {
    return this.truncate(maxFractionCount).let {
        NSString.stringWithFormat("%,.${maxFractionCount}f", it)
    }
}

actual fun Double.formatNumber(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        locale = NSLocale.currentLocale
    }
    return formatter.stringFromNumber(NSNumber(this)) ?: "$this"
}

fun uiColor(hex: Long): UIColor {
    val alpha = ((hex shr 24) and 0xFF) / 255.0
    val red = ((hex shr 16) and 0xFF) / 255.0
    val green = ((hex shr 8) and 0xFF) / 255.0
    val blue = (hex and 0xFF) / 255.0

    return UIColor(
        red = red,
        green = green,
        blue = blue,
        alpha = alpha
    )
}