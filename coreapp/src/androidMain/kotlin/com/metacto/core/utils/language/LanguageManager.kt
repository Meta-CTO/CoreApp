package com.metacto.core.utils.language

import java.util.Locale

class LanguageManager : ILanguageManager {

    override fun getDeviceIso3Lang(): String {
        return Locale.getDefault().isO3Language
    }
}