package com.metacto.core.extensions

fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    return apply {
        if (condition) block()
    }
}