package com.metacto.core.extensions

import java.text.DecimalFormat
import java.util.Locale

actual fun Double.format(decimalsCount: Int): String {
    return String.format(Locale.US, "%.${decimalsCount}f", this)
}

actual fun Double.formatToComma(maxFractionCount: Int): String {
    return this.truncate(maxFractionCount).let {
        String.format("%.${maxFractionCount}f", it)
    }
}

actual fun Double.formatNumber(maxFractionCount: Int): String {
    val pattern = buildString {
        append("#,##0")
        if (maxFractionCount > 0) {
            append(".")
            repeat(maxFractionCount) { append("0") }
        }
    }
    val formatter = DecimalFormat(pattern)
    return formatter.format(this)
}


actual fun Double.formatAmount(): String {
    val formatter = if (this.toString().contains('.')) {
        DecimalFormat("#,###.00")
    } else {
        DecimalFormat("#,###")
    }
    return formatter.format(this)
}
