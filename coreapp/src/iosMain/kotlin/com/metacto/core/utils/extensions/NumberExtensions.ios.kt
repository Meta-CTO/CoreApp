package com.metacto.core.utils.extensions

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Double.formatToMaxOneDecimal(): String =
    NSString.stringWithFormat("%.1f", this).removeSuffix(".0")

actual fun Double.formatToMaxTwoDecimals(): String =
    NSString.stringWithFormat("%.2f", this)

actual fun Double.formatToComma(): String =
    NSString.stringWithFormat("%,.0f", this)

actual fun Double.formatToCurrency(): String =
    NSString.stringWithFormat("$%,.0f", this)

actual fun Float.formatToMaxOneDecimal(): String =
    NSString.stringWithFormat("%.1f", this).removeSuffix(".0")

actual fun Float.formatToMaxTwoDecimals(): String =
    NSString.stringWithFormat("%.2f", this)

actual fun Float.formatToComma(): String =
    NSString.stringWithFormat("%,.0f", this)

actual fun Float.formatToCurrency(): String =
    NSString.stringWithFormat("$%,.0f", this)

actual fun Float.format(decimalsCount: Int): String =
    this.truncate(decimalsCount).let { NSString.stringWithFormat("%.${decimalsCount}f", it) }

actual fun Double.format(decimalsCount: Int): String =
    this.truncate(decimalsCount).let { NSString.stringWithFormat("%.${decimalsCount}f", it) }
