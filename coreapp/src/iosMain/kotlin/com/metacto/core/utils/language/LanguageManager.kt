package com.metacto.core.utils.language

import com.metacto.core.utils.extensions.nullIfEmpty
import platform.Foundation.NSBundle
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

class LanguageManager : ILanguageManager {

    override fun getDeviceIso3Lang(): String {
        return NSBundle().preferredLocalizations()?.firstOrNull()?.toString() ?: "en"
    }

    override fun getDeviceIso2Lang(): String {
        return NSLocale.currentLocale.languageCode.nullIfEmpty() ?: "en"
    }
}