package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.language.Language
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <R> ifTrue(expression: Boolean, block: () -> R): R? {
    return if (expression) block() else null
}

fun CancellableContinuation<*>.cancelIfActive() {
    if (isActive) cancel()
}

fun <T> CoroutineScope.asyncIf(
    condition: Boolean,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T>? {
    return if (condition) {
        async(
            context = context,
            start = start,
            block = block
        )
    } else {
        null
    }
}

expect fun getPlatformType(): PlatformType

expect fun randomUUID(): String

expect fun getSystemLanguage(): Language