@file:OptIn(
    ExperimentalForeignApi::class,
    ExperimentalSettingsApi::class,
    ExperimentalSettingsImplementation::class
)

package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
import com.metacto.strapikmm.datasource.network.KtorClientFactory
import com.metacto.strapikmm.datasource.network.services.strapi.StrapiService
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.sharedpreference.KmmPreference
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

// DON'T REMOVE COMMENTED CODE HERE, WE NEED TO MIGRATE OLD KEYCHAIN TO NEW KEYCHAIN
// WE HAVE TO WAIT FOR THE NEW KEYCHAIN TO BE IMPLEMENTED IN THE LIBRARY FIRST
actual open class RepositoriesFactory<T : SerializableNetworkError> constructor(
    actual val environment: CoreEnvironment,
    actual val appStorageName: String,
    actual val shouldShowActualErrorMessages: Boolean,
    actual val errorClass: KClass<T>
) {

    private val oldKeyChainStore = KeychainSettings(service = appStorageName)
    private val newKeyChainStore = KeychainSettings(
        kSecAttrService to CFBridgingRetain(appStorageName),
        kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlock,
    )

    actual val sharedPreference = KmmPreference(
        preferences = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults()),
        encryptedPreferences = oldKeyChainStore
    )

    private val ktorClientFactory = KtorClientFactory(
        networkLogLevel = environment.networkLogLevel,
        preference = sharedPreference,
        shouldShowActualErrorMessages = shouldShowActualErrorMessages
    )

    actual val strapiService = StrapiService(
        httpClient = ktorClientFactory.build(errorClass),
        baseUrl = environment.baseUrl,
        kmmPreference = sharedPreference
    )

    init {
        migrate(oldKeyChainStore, newKeyChainStore)
    }
}

// DON'T REMOVE COMMENTED CODE HERE, WE NEED TO MIGRATE OLD KEYCHAIN TO NEW KEYCHAIN
// WE HAVE TO WAIT FOR THE NEW KEYCHAIN TO BE IMPLEMENTED IN THE LIBRARY FIRST

private fun migrate(oldKeyChainStore: KeychainSettings, newKeyChainStore: KeychainSettings) {
    if (newKeyChainStore.hasKey("_version")) return

    oldKeyChainStore.keys.forEach { key ->
        // Check each type and store accordingly
        val oldValue: Any? = when {
            oldKeyChainStore.getIntOrNull(key) != null -> oldKeyChainStore.getIntOrNull(key)
            oldKeyChainStore.getLongOrNull(key) != null -> oldKeyChainStore.getLongOrNull(key)
            oldKeyChainStore.getStringOrNull(key) != null -> oldKeyChainStore.getStringOrNull(key)
            oldKeyChainStore.getFloatOrNull(key) != null -> oldKeyChainStore.getFloatOrNull(key)
            oldKeyChainStore.getDoubleOrNull(key) != null -> oldKeyChainStore.getDoubleOrNull(key)
            oldKeyChainStore.getBooleanOrNull(key) != null -> oldKeyChainStore.getBooleanOrNull(key)
            else -> null
        }

        oldValue?.let {
            when (it) {
                is Int -> newKeyChainStore.putInt(key, it)
                is Long -> newKeyChainStore.putLong(key, it)
                is String -> newKeyChainStore.putString(key, it)
                is Float -> newKeyChainStore.putFloat(key, it)
                is Double -> newKeyChainStore.putDouble(key, it)
                is Boolean -> newKeyChainStore.putBoolean(key, it)
            }
        }
    }

    oldKeyChainStore.clear()
    newKeyChainStore.putString("_version", "1")
}