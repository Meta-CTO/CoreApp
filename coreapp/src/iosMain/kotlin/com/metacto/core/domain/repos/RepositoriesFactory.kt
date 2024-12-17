@file:OptIn(ExperimentalForeignApi::class, ExperimentalSettingsApi::class)

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

@OptIn(ExperimentalSettingsImplementation::class)
actual open class RepositoriesFactory<T: SerializableNetworkError> constructor(
    actual val environment: CoreEnvironment,
    actual val appStorageName: String,
    actual val shouldShowActualErrorMessages: Boolean,
    actual val errorClass: KClass<T>
) {
    actual val sharedPreference = KmmPreference(
        preferences = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults()),
        encryptedPreferences = KeychainSettings(
            kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlock,
            kSecAttrService to CFBridgingRetain(appStorageName)
        )
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
}