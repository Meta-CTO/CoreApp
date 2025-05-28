package com.sampleApp.app.constants

import com.metacto.core.CoreEnvironment
import com.metacto.strapikmm.datasource.network.NetworkLogLevel

object AppEnvironment {
    fun dev() = CoreEnvironment(
        baseUrl = "https://dev-api.satyadating.com/api",
        title = "DEV",
        networkLogLevel = NetworkLogLevel.ALL,
        iosAppStoreId = "id310633997",
        androidAppId = "com.sampleApp.app.dev",
        forceUpdateRemoteConfigKey = "RECOMMENDED_APP_VERSIONS",
        appConfigurationExpirationInMinutes = 1 * 60 * 24,
        currentAppConfigurationVersion = 1,
        askRemoteNotificationPermissionOnStart = true,
        enableSwipeToGoBack = true
    )

    fun prod() = CoreEnvironment(
        baseUrl = "https://dev-api.satyadating.com/api",
        title = "PRODUCTION",
        networkLogLevel = NetworkLogLevel.ALL,
        iosAppStoreId = "id310633997",
        androidAppId = "com.sampleApp.app.prod",
        appConfigurationExpirationInMinutes = 1 * 60 * 24,
        currentAppConfigurationVersion = 1,
        askRemoteNotificationPermissionOnStart = true,
        enableSwipeToGoBack = true
    )
}
