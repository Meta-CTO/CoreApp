package com.metacto.core.utils.extensions

import java.text.DecimalFormat

actual fun Double.format(decimalsCount: Int): String {
    return if (this == this.toInt().toDouble()) {
        this.toInt().toString()
    } else {
        this.truncate(decimalsCount).let {
            String.format("%.${decimalsCount}f", it)
        }
    }
}

actual fun Double.formatToComma(): String {
    return DecimalFormat("#,##0").format(this)
}
