@file:OptIn(
    ExperimentalForeignApi::class,
    ExperimentalSettingsApi::class,
    ExperimentalSettingsImplementation::class
)

package com.metacto.core.domain.repos

import com.metacto.core.CoreConfigs
import com.metacto.core.domain.CoreConstants
import com.metacto.kmm.network.KtorClientFactory
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import com.metacto.kmm.network.services.HttpService
import com.metacto.kmm.sharedpreferences.KmmPreference
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSUserDefaults
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlock
import platform.Security.kSecAttrService
import kotlin.reflect.KClass


actual open class RepositoriesFactory<T : SerializableNetworkError>(
    actual val coreConfigs: CoreConfigs,
    actual val appStorageName: String,
    actual val shouldShowActualErrorMessages: Boolean,
    actual val networkUserAgent: String?,
    actual val errorClass: KClass<T>
) {

    private val oldKeyChainStore by lazy { KeychainSettings(service = appStorageName) }
    @OptIn(ExperimentalForeignApi::class)
    private val newKeyChainStore by lazy {
        KeychainSettings(
            kSecAttrService to CFBridgingRetain("${appStorageName}_${coreConfigs.storageName.lowercase()}_keychain"),
            kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlock,
        )
    }

    actual val sharedPreference = KmmPreference(
        preferences = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults()),
        encryptedPreferences = newKeyChainStore
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

    init {
        migrate(oldKeyChainStore, newKeyChainStore)
    }
}

private fun migrate(oldKeyChainStore: KeychainSettings, newKeyChainStore: KeychainSettings) {
    // Check if the new store already has the "${CoreConstants.KEYCHAIN_PREFERENCE_VERSION}" key. If it does, migration is not needed.
    if (newKeyChainStore.hasKey(CoreConstants.KEYCHAIN_PREFERENCE_VERSION)) {
        return
    }

    // Iterate over all keys in the old keychain store
    oldKeyChainStore.keys.forEach { key ->
        // Determine the type of value associated with the key in the old keychain store
        val oldValue: Any? = when {
            oldKeyChainStore.getLongOrNull(key) != null -> {
                oldKeyChainStore.getLongOrNull(key)
            }

            oldKeyChainStore.getIntOrNull(key) != null -> {
                oldKeyChainStore.getIntOrNull(key)
            }

            oldKeyChainStore.getStringOrNull(key) != null -> {
                oldKeyChainStore.getStringOrNull(key)
            }

            oldKeyChainStore.getFloatOrNull(key) != null -> {
                oldKeyChainStore.getFloatOrNull(key)
            }

            oldKeyChainStore.getDoubleOrNull(key) != null -> {
                oldKeyChainStore.getDoubleOrNull(key)
            }

            oldKeyChainStore.getBooleanOrNull(key) != null -> {
                oldKeyChainStore.getBooleanOrNull(key)
            }

            else -> {
                null
            }
        }

        // If the old value is not null, migrate it to the new keychain store
        oldValue?.let {
            when (it) {
                is Long -> newKeyChainStore.putLong(key, it)
                is Int -> newKeyChainStore.putInt(key, it)
                is String -> newKeyChainStore.putString(key, it)
                is Float -> newKeyChainStore.putFloat(key, it)
                is Double -> newKeyChainStore.putDouble(key, it)
                is Boolean -> newKeyChainStore.putBoolean(key, it)
            }
        }
    }

    // Clear the old keychain store after migration
    oldKeyChainStore.clear()

    // Mark the new keychain store as migrated by setting the "_version" key
    newKeyChainStore.putString(CoreConstants.KEYCHAIN_PREFERENCE_VERSION, "1")
}