package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import dev.icerock.moko.resources.AssetResource
import java.util.UUID

actual fun getPlatformType(): PlatformType {
    return PlatformType.ANDROID
}

actual fun randomUUID() = UUID.randomUUID().toString()

fun AssetResource.getAbsolutePath() = "file:///android_asset/$path"