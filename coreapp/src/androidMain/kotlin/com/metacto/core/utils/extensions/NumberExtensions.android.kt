package com.metacto.core.utils.extensions

actual fun Double.format(decimalsCount: Int): String {
    return this.truncate(decimalsCount).let {
        String.format("%.${decimalsCount}f", it)
    }
}

actual fun Double.formatToComma(maxFractionCount: Int): String {
    return String.format("%,.${maxFractionCount}f", this)
}
