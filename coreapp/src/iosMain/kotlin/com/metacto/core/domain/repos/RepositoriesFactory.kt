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
import com.metacto.strapikmm.util.Logger
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
//    private val newKeyChainStore = KeychainSettings(
//        kSecAttrService to CFBridgingRetain(appStorageName),
//        kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlock,
//    )

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

//    init {
//        migrate(oldKeyChainStore, newKeyChainStore)
//    }
}

// DON'T REMOVE COMMENTED CODE HERE, WE NEED TO MIGRATE OLD KEYCHAIN TO NEW KEYCHAIN
// WE HAVE TO WAIT FOR THE NEW KEYCHAIN TO BE IMPLEMENTED IN THE LIBRARY FIRST

private fun migrate(oldKeyChainStore: KeychainSettings, newKeyChainStore: KeychainSettings) {
    // Logger for debugging the migration process
    val logger = Logger("RepositoriesFactory migrate")

    // Check if the new store already has the "_version" key. If it does, migration is not needed.
    if (newKeyChainStore.hasKey("_version")) {
        logger.log("Migration skipped: newKeyChainStore already contains '_version'")
        return
    }

    // Iterate over all keys in the old keychain store
    oldKeyChainStore.keys.forEach { key ->
        logger.log("Processing key: $key")

        // Determine the type of value associated with the key in the old keychain store
        val oldValue: Any? = when {
            oldKeyChainStore.getIntOrNull(key) != null -> {
                logger.log("Key $key has type Int")
                oldKeyChainStore.getIntOrNull(key)
            }
            oldKeyChainStore.getLongOrNull(key) != null -> {
                logger.log("Key $key has type Long")
                oldKeyChainStore.getLongOrNull(key)
            }
            oldKeyChainStore.getStringOrNull(key) != null -> {
                logger.log("Key $key has type String")
                oldKeyChainStore.getStringOrNull(key)
            }
            oldKeyChainStore.getFloatOrNull(key) != null -> {
                logger.log("Key $key has type Float")
                oldKeyChainStore.getFloatOrNull(key)
            }
            oldKeyChainStore.getDoubleOrNull(key) != null -> {
                logger.log("Key $key has type Double")
                oldKeyChainStore.getDoubleOrNull(key)
            }
            oldKeyChainStore.getBooleanOrNull(key) != null -> {
                logger.log("Key $key has type Boolean")
                oldKeyChainStore.getBooleanOrNull(key)
            }
            else -> {
                logger.log("Key $key has an unknown or null value type")
                null
            }
        }

        // If the old value is not null, migrate it to the new keychain store
        oldValue?.let {
            logger.log("Migrating key: $key with value: $it")

            when (it) {
                is Int -> newKeyChainStore.putInt(key, it).also { logger.log("Migrated key $key as Int") }
                is Long -> newKeyChainStore.putLong(key, it).also { logger.log("Migrated key $key as Long") }
                is String -> newKeyChainStore.putString(key, it).also { logger.log("Migrated key $key as String") }
                is Float -> newKeyChainStore.putFloat(key, it).also { logger.log("Migrated key $key as Float") }
                is Double -> newKeyChainStore.putDouble(key, it).also { logger.log("Migrated key $key as Double") }
                is Boolean -> newKeyChainStore.putBoolean(key, it).also { logger.log("Migrated key $key as Boolean") }
            }
        }
    }

    // Clear the old keychain store after migration
    oldKeyChainStore.clear()
    logger.log("Old keychain store cleared")

    // Mark the new keychain store as migrated by setting the "_version" key
    newKeyChainStore.putString("_version", "1")
    logger.log("Migration complete: '_version' set in newKeyChainStore")
}
