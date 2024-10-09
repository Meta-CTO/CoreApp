package com.metacto.core.utils.extensions

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Double.format(decimalsCount: Int): String {
    return this.truncate(decimalsCount).let {
        NSString.stringWithFormat("%.${decimalsCount}f", it)
    }
}

actual fun Double.formatToComma(maxFractionCount: Int): String {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterDecimalStyle
    formatter.maximumFractionDigits = maxFractionCount.toULong()
    val number = NSNumber(this)
    return formatter.stringFromNumber(number) ?: this.toString()
}