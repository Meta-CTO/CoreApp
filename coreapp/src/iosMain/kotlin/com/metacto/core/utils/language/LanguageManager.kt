package com.metacto.core.utils.language

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.metacto.core.utils.extensions.getObject
import com.metacto.core.utils.extensions.getSystemLanguage
import com.metacto.core.utils.extensions.putObject
import com.metacto.core.utils.language.ILanguageManager.Companion.KEY_APP_LANG
import com.metacto.strapikmm.sharedpreference.KmmPreference
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

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

        // Change user defaults language
        NSUserDefaults.standardUserDefaults.run {
            setValue(listOf(language.code), forKey = "AppleLanguages")
            synchronize()
        }
    }

    override fun currentLanguageAsState(): State<Language?> {
        return currentLanguage
    }
}