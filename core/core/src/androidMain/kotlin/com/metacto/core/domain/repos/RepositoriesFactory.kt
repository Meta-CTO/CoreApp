package com.metacto.core.domain.repos

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.metacto.core.CoreConfigs
import com.metacto.kmm.network.KtorClientFactory
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import com.metacto.kmm.network.services.HttpService
import com.metacto.kmm.sharedpreferences.KmmPreference
import com.russhwolf.settings.SharedPreferencesSettings
import kotlin.reflect.KClass

actual open class RepositoriesFactory<T : SerializableNetworkError>(
    context: Context,
    actual val coreConfigs: CoreConfigs,
    actual val appStorageName: String,
    actual val shouldShowActualErrorMessages: Boolean,
    actual val networkUserAgent: String?,
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
        networkLogLevel = coreConfigs.logLevel,
        preference = sharedPreference,
        shouldShowActualErrorMessages = shouldShowActualErrorMessages,
        userAgent = networkUserAgent
    )

    actual val httpService = HttpService(
        baseUrl = coreConfigs.baseUrl,
        kmmPreference = sharedPreference,
        httpClient = ktorClientFactory.build(
            errorClass = errorClass,
            configure = coreConfigs.httpClientConfiguration
        )
    )
}