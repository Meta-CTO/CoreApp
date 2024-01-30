package com.metacto.core.utils.extensions

import java.text.DecimalFormat

actual fun formatToMaxOneDecimal(number: Double): String =
    DecimalFormat("#.#").format(number)

actual fun formatToMaxTwoDecimals(number: Double): String =
    DecimalFormat("##0.00").format(number)

actual fun formatToComma(number: Double): String =
    DecimalFormat("#,##0").format(number)

actual fun formatToCurrency(number: Double): String =
    DecimalFormat("$#,##0").format(number)