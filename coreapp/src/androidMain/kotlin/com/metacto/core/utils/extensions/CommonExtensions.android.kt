package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import dev.icerock.moko.resources.AssetResource
import java.util.UUID

actual fun getPlatformType(): PlatformType {
    return PlatformType.ANDROID
}

actual fun randomUUID() = UUID.randomUUID().toString()

fun AssetResource.getAbsolutePath() = "file:///android_asset/$path"

// NOTE: This is not the actual implementation of mainContinuation, but it's a placeholder to make the project compile.
// TODO: Handle this later
actual inline fun <T1, T2> mainContinuation(noinline block: (T1, T2) -> Unit): (T1, T2) -> Unit {
    return { arg1, arg2 ->
        block.invoke(arg1, arg2)
    }
}

// NOTE: This is not the actual implementation of mainContinuation, but it's a placeholder to make the project compile.
// TODO: Handle this later
actual inline fun <T1> mainContinuation(noinline block: (T1) -> Unit): (T1) -> Unit {
    return { arg1 ->
        block.invoke(arg1)
    }
}