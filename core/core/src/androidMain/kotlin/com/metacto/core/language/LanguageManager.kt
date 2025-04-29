package com.metacto.core.language

import android.os.LocaleList
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.metacto.core.extensions.getSystemLanguage
import com.metacto.core.language.ILanguageManager.Companion.KEY_APP_LANG
import com.metacto.core.prefs.KmmPreference
import com.metacto.core.prefs.getObject
import com.metacto.core.prefs.putObject
import java.util.Locale

class LanguageManager(
    private val kmmPreference: KmmPreference
) : ILanguageManager {
    private var currentLanguage = mutableStateOf<Language?>(null)

    override fun getCurrentLanguage(): Language {
        // Load language if not loaded
        if (currentLanguage.value == null) {
            currentLanguage.value = kmmPreference.getObject(KEY_APP_LANG) ?: getSystemLanguage()
        }

        // Return
        return currentLanguage.value!!
    }

    override fun changeLanguage(language: Language) {
        // Cache this language
        currentLanguage.value = language
        kmmPreference.putObject(KEY_APP_LANG, language)

        // Update the locale
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        LocaleList.setDefault(LocaleList(locale))
    }

    override fun currentLanguageAsState(): State<Language?> {
        return currentLanguage
    }
}