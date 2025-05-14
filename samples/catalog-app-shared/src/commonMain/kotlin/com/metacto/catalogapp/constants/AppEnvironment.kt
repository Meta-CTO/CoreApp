package com.metacto.catalogapp.constants

import com.metacto.core.CoreConfigs
import com.metacto.core.ui.CoreUIConfigs
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import com.metacto.catalogapp.deepLinks.DEEP_LINK_PARSERS
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
            storageName = "MetaCtoSampleDev",
            logLevel = LogLevel.ALL,
            shouldShowActualErrorMessages = true,
            errorClass = SerializableNetworkError::class,
            appConfigurationExpirationInMinutes = 1.hours.inWholeMinutes,
            deepLinkParsers = DEEP_LINK_PARSERS
        ),
    )

    data object Prod : AppEnvironment(
        coreConfigs = CoreConfigs(
            baseUrl = "https://api.satyadating.com/api",
            storageName = "MetaCtoSampleProd",
            logLevel = LogLevel.NONE,
            shouldShowActualErrorMessages = false,
            errorClass = SerializableNetworkError::class,
            appConfigurationExpirationInMinutes = 1.days.inWholeMinutes,
            deepLinkParsers = DEEP_LINK_PARSERS
        )
    )
}