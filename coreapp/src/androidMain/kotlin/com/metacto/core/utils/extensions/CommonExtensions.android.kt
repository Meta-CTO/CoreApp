package com.metacto.core.utils.extensions

import android.util.LayoutDirection
import androidx.core.text.layoutDirection
import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.language.Language
import java.util.Locale
import java.util.UUID

actual fun getPlatformType(): PlatformType {
    return PlatformType.ANDROID
}

actual fun randomUUID() = UUID.randomUUID().toString()

actual fun getSystemLanguage(): Language {
    val locale = Locale.getDefault()

    return Language(
        code = locale.language,
        name = locale.displayName,
        isRtl = locale.layoutDirection == LayoutDirection.RTL
    )
}