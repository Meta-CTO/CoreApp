package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
import com.metacto.strapikmm.datasource.network.KtorClientFactory
import com.metacto.strapikmm.datasource.network.services.strapi.StrapiService
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.sharedpreference.KmmPreference
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import platform.Foundation.NSUserDefaults
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
        encryptedPreferences = KeychainSettings(appStorageName)
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