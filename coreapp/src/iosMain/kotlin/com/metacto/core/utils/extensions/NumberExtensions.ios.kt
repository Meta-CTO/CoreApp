package com.metacto.core.utils.extensions

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

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