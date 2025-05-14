package com.metacto.playground.constants

import com.metacto.core.CoreConfigs
import com.metacto.core.ui.CoreUIConfigs
import com.metacto.kmm.logger.LogLevel
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

sealed class AppEnvironment(
    val coreConfigs: CoreConfigs,
    val coreUIConfigs: CoreUIConfigs = CoreUIConfigs(
        enableSwipeToGoBack = true
    )
) {
    data object Dev : AppEnvironment(
        coreConfigs = CoreConfigs(
            baseUrl = "https://dev-api.satyadating.com/api",
            storageName = "CatalogAppDev",
            logLevel = LogLevel.ALL,
            shouldShowActualErrorMessages = true,
            appConfigurationExpirationInMinutes = 1.hours.inWholeMinutes,
        ),
    )

    data object Prod : AppEnvironment(
        coreConfigs = CoreConfigs(
            baseUrl = "https://api.satyadating.com/api",
            storageName = "CatalogAppProd",
            logLevel = LogLevel.NONE,
            shouldShowActualErrorMessages = false,
            appConfigurationExpirationInMinutes = 1.days.inWholeMinutes,
        )
    )
}