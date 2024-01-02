package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType

fun <R> ifTrue(expression: Boolean, block: () -> R): R? {
    return if (expression) block() else null
}

expect fun getPlatformType(): PlatformType

expect fun randomUUID(): String