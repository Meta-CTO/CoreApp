package com.metacto.core.utils.extensions

import java.text.DecimalFormat

actual fun Double.format(decimalsCount: Int): String {
    return this.truncate(decimalsCount).let {
        String.format("%.${decimalsCount}f", it)
    }
}

actual fun Double.formatToComma(maxFractionCount: Int): String {
    return this.truncate(maxFractionCount).let {
        String.format("%.${maxFractionCount}f", it)
    }
}

actual  fun Double.formatNumber(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this)
}
