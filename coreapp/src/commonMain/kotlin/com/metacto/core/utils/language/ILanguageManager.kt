package com.metacto.core.utils.language

import androidx.compose.runtime.State

val Class = ILanguageManager::class

interface ILanguageManager {
    fun getCurrentLanguage(): Language
    fun changeLanguage(language: Language)
    fun currentLanguageAsState(): State<Language?>

    companion object {
        private val CLASS_NAME = ILanguageManager::class.simpleName
        internal val KEY_APP_LANG = "$CLASS_NAME-appLang"
    }
}