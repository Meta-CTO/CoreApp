package com.metacto.core.utils.language

import com.metacto.core.utils.extensions.nullIfEmpty
import java.util.Locale

class LanguageManager : ILanguageManager {

    override fun getDeviceIso3Lang(): String {
        return Locale.getDefault().isO3Language
    }

    override fun getDeviceIso2Lang(): String {
        return Locale.getDefault().language.nullIfEmpty() ?: "en"
    }
}