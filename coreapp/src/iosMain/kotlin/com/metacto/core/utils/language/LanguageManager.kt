package com.metacto.core.utils.language

import platform.Foundation.NSBundle

class LanguageManager : ILanguageManager {

    override fun getDeviceIso3Lang(): String {
        return NSBundle().preferredLocalizations()?.firstOrNull()?.toString() ?: "en"
    }
}