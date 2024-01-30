package com.metacto.core.utils.extensions

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun formatToMaxOneDecimal(number: Double): String =
    NSString.stringWithFormat("%.1f", number).removeSuffix(".0")

actual fun formatToMaxTwoDecimals(number: Double): String =
    NSString.stringWithFormat("%.2f", number)

actual fun formatToComma(number: Double): String =
    NSString.stringWithFormat("%,.0f", number)

actual fun formatToCurrency(number: Double): String =
    NSString.stringWithFormat("$%,.0f", number)