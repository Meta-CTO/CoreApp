package com.sampleApp.app.constants

import com.metacto.core.CoreEnvironment
import com.swensonhe.strapikmm.datasource.network.NetworkLogLevel

object AppEnvironment {
    fun dev() = CoreEnvironment(
        baseUrl = "https://dev-api.sampleApp.com/api",
        title = "DEV",
        networkLogLevel = NetworkLogLevel.ALL
    )

    fun prod() = CoreEnvironment(
        baseUrl = "https://api.sampleApp.com/api",
        title = "PRODUCTION",
        networkLogLevel = NetworkLogLevel.NONE
    )
}