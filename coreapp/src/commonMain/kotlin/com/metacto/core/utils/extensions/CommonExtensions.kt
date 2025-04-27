package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.language.Language
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <R> ifTrue(expression: Boolean, block: () -> R): R? {
    return if (expression) block() else null
}

fun CancellableContinuation<*>.cancelIfActive() {
    if (isActive) cancel()
}

inline fun <reified T> T.toMap(): Map<String, String> {
    val jsonElement = Json.encodeToJsonElement(this)
    return jsonElement.jsonObject.toMap()
}

fun JsonObject.toMap(): Map<String, String> {
    return entries.associate { (key, jsonElement) ->
        key to (jsonElement.jsonPrimitive.contentOrNull ?: jsonElement.toString())
    }
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