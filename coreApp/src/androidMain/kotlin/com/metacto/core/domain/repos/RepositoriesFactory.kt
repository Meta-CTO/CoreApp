package com.metacto.core.domain.repos

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.metacto.core.CoreEnvironment
import com.russhwolf.settings.SharedPreferencesSettings
import com.swensonhe.strapikmm.datasource.network.KtorClientFactory
import com.swensonhe.strapikmm.datasource.network.services.strapi.StrapiService
import com.swensonhe.strapikmm.sharedpreference.KmmPreference

actual open class RepositoriesFactory constructor(
    context: Context,
    actual val environment: CoreEnvironment,
    actual val appStorageName: String
) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferencesDelegate = context.getSharedPreferences(
        appStorageName,
        Context.MODE_PRIVATE
    )
    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        "${appStorageName}_encrypted",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual val sharedPreference = KmmPreference(
        preferences = SharedPreferencesSettings(sharedPreferencesDelegate),
        encryptedPreferences = SharedPreferencesSettings(encryptedSharedPreferences)
    )

    private val ktorClientFactory = KtorClientFactory(
        networkLogLevel = environment.networkLogLevel,
        preference = sharedPreference
    )

    actual val strapiService = StrapiService(
        httpClient = ktorClientFactory.build(),
        baseUrl = environment.baseUrl,
        kmmPreference = sharedPreference,
        context = context
    )
}