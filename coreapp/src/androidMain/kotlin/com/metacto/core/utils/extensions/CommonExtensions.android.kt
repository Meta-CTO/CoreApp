package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import java.util.UUID

actual fun getPlatformType(): PlatformType {
    return PlatformType.ANDROID
}

actual fun randomUUID() = UUID.randomUUID().toString()