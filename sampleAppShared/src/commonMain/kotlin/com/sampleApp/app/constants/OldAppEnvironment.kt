package com.sampleApp.app.constants

import com.metacto.core.CoreEnvironment as OldCoreEnvironment
import com.metacto.strapikmm.datasource.network.NetworkLogLevel
import com.metacto.core.environment.CoreEnvironment

object OldAppEnvironment {
    fun dev() = OldCoreEnvironment(
        baseUrl = "https://dev-api.satyadating.com/api",
        title = "DEV",
        networkLogLevel = NetworkLogLevel.ALL,
        iosAppStoreId = "id310633997",
        forceUpdateRemoteConfigKey = "RECOMMENDED_APP_VERSIONS",
        appConfigurationExpirationInMinutes = 1 * 60 * 24,
        currentAppConfigurationVersion = 1,
        enableSwipeToGoBack = true
    )

    fun prod() = OldCoreEnvironment(
        baseUrl = "https://dev-api.satyadating.com/api",
        title = "PRODUCTION",
        networkLogLevel = NetworkLogLevel.ALL,
        iosAppStoreId = "id310633997",
        appConfigurationExpirationInMinutes = 1 * 60 * 24,
        currentAppConfigurationVersion = 1,
        enableSwipeToGoBack = true
    )
}

sealed class AppEnvironment(
    override val title: String
) : CoreEnvironment(
    title = title,
) {
    data object Dev : AppEnvironment(
        title = "SampleAppDev",
    )

    data object Prod : AppEnvironment(
        title = "SampleApp",
    )
}