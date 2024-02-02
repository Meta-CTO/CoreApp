package com.metacto.core.utils.extensions

import java.text.DecimalFormat

actual fun Double.formatToMaxOneDecimal(): String =
    DecimalFormat("#.#").format(this)

actual fun Double.formatToMaxTwoDecimals(): String =
    DecimalFormat("##0.00").format(this)

actual fun Double.formatToComma(): String =
    DecimalFormat("#,##0").format(this)

actual fun Double.formatToCurrency(): String =
    DecimalFormat("$#,##0").format(this)

actual fun Float.formatToMaxOneDecimal(): String =
    DecimalFormat("#.#").format(this)

actual fun Float.formatToMaxTwoDecimals(): String =
    DecimalFormat("##0.00").format(this)

actual fun Float.formatToComma(): String =
    DecimalFormat("#,##0").format(this)

actual fun Float.formatToCurrency(): String =
    DecimalFormat("$#,##0").format(this)

actual fun Float.format(decimalsCount: Int): String =
    this.truncate(decimalsCount).let { String.format("%.${decimalsCount}f", it) }

actual fun Double.format(decimalsCount: Int): String =
    this.truncate(decimalsCount).let { String.format("%.${decimalsCount}f", it) }

