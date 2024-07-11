package com.metacto.core.domain.repos

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.metacto.core.CoreEnvironment
import com.metacto.strapikmm.datasource.network.KtorClientFactory
import com.metacto.strapikmm.datasource.network.services.strapi.StrapiService
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.sharedpreference.KmmPreference
import com.russhwolf.settings.SharedPreferencesSettings
import kotlin.reflect.KClass

actual open class RepositoriesFactory<T : SerializableNetworkError> constructor(
    context: Context,
    actual val environment: CoreEnvironment,
    actual val appStorageName: String,
    actual val shouldShowActualErrorMessages: Boolean,
    actual val errorClass: KClass<T>
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
        preference = sharedPreference,
        shouldShowActualErrorMessages = shouldShowActualErrorMessages
    )

    actual val strapiService = StrapiService(
        httpClient = ktorClientFactory.build(errorClass),
        baseUrl = environment.baseUrl,
        kmmPreference = sharedPreference,
        context = context
    )
}