package com.metacto.core.di

import com.metacto.core.CoreConfigs
import com.metacto.core.language.ILanguageManager
import com.metacto.core.language.LanguageManager
import com.metacto.kmm.sharedpreferences.KmmPreference
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSUserDefaults
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlock
import platform.Security.kSecAttrService

@OptIn(
    ExperimentalSettingsImplementation::class,
    ExperimentalSettingsApi::class,
    ExperimentalForeignApi::class
)
internal actual fun platformModule() = module {
    // iOS specific dependencies can be added here

    single<ILanguageManager> {
        LanguageManager(get())
    }

    single<KmmPreference> {
        val storageName = get<CoreConfigs>().storageName

        val preferences = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults())
        val encryptedPreferences = KeychainSettings(
            kSecAttrService to CFBridgingRetain("${storageName}_keychain"),
            kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlock,
        )

        KmmPreference(
            preferences = preferences,
            encryptedPreferences = encryptedPreferences
        )
    }
}