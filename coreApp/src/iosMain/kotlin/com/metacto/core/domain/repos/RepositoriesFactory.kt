package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.swensonhe.strapikmm.datasource.network.KtorClientFactory
import com.swensonhe.strapikmm.datasource.network.services.strapi.StrapiService
import com.swensonhe.strapikmm.sharedpreference.KmmPreference
import platform.Foundation.NSUserDefaults

@OptIn(ExperimentalSettingsImplementation::class)
actual open class RepositoriesFactory constructor(
    actual val environment: CoreEnvironment,
    actual val appStorageName: String
) {
    actual val sharedPreference = KmmPreference(
        preferences = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults()),
        encryptedPreferences = KeychainSettings(appStorageName)
    )

    private val ktorClientFactory = KtorClientFactory(
        networkLogLevel = environment.networkLogLevel,
        preference = sharedPreference
    )

    actual val strapiService = StrapiService(
        httpClient = ktorClientFactory.build(),
        baseUrl = environment.baseUrl,
        kmmPreference = sharedPreference
    )
}