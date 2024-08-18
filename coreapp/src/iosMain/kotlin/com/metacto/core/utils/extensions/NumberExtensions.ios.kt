package com.metacto.core.utils.extensions

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Double.format(decimalsCount: Int): String {
    return if (this == this.toInt().toDouble()) {
        this.toInt().toString()
    } else {
        this.truncate(decimalsCount).let {
            NSString.stringWithFormat("%.${decimalsCount}f", it)
        }
    }
}

actual fun Double.formatToComma(): String {
    return NSString.stringWithFormat("%,.0f", this)
}
