package com.sampleApp.app.constants

import com.metacto.core.CoreEnvironment
import com.metacto.strapikmm.datasource.network.NetworkLogLevel

object AppEnvironment {
    fun dev() = CoreEnvironment(
        baseUrl = "https://dev-api.sampleApp.com/api",
        title = "DEV",
        networkLogLevel = NetworkLogLevel.ALL,
        storeAppId = "id310633997",
        forceUpdateRemoteConfigKey = "RECOMMENDED_APP_VERSIONS"

    )

    fun prod() = CoreEnvironment(
        baseUrl = "https://api.sampleApp.com/api",
        title = "PRODUCTION",
        networkLogLevel = NetworkLogLevel.NONE,
        storeAppId = "id310633997",
        forceUpdateRemoteConfigKey = "RECOMMENDED_APP_VERSIONS"
    )
}