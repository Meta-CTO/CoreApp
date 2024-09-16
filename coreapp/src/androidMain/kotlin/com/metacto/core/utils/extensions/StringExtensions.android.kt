package com.metacto.core.utils.extensions

actual fun String.formatNatively(vararg args: Any): String {
    return String.format(this, *args)
}