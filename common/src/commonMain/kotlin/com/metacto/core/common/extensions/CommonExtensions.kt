package com.metacto.core.common.extensions

fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    return apply {
        if (condition) block()
    }
}