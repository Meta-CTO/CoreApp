package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.language.Language
import kotlinx.coroutines.CancellableContinuation

fun <R> ifTrue(expression: Boolean, block: () -> R): R? {
    return if (expression) block() else null
}

fun CancellableContinuation<*>.cancelIfActive() {
    if (isActive) cancel()
}


expect fun getPlatformType(): PlatformType

expect fun randomUUID(): String

expect fun getSystemLanguage(): Language