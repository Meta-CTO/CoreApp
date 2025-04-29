package com.metacto.core.dii

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.metacto.core.environment.CoreEnvironment
import com.metacto.core.language.ILanguageManager
import com.metacto.core.language.LanguageManager
import com.metacto.core.prefs.KmmPreference
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Android specific dependencies can be added here

    single<ILanguageManager> {
        LanguageManager(get())
    }

    single<KmmPreference> {
        val app = androidApplication()
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val storageName = get<CoreEnvironment>().title

        val sharedPreferencesDelegate = app.getSharedPreferences(
            "${storageName}_normal",
            Context.MODE_PRIVATE
        )
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "${storageName}_encrypted",
            masterKeyAlias,
            app,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        KmmPreference(
            preferences = SharedPreferencesSettings(sharedPreferencesDelegate),
            encryptedPreferences = SharedPreferencesSettings(encryptedSharedPreferences)
        )
    }
}