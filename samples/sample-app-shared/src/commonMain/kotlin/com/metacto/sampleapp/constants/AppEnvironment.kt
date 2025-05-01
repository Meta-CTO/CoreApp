package com.metacto.sampleapp.constants

import com.metacto.core.CoreConfigs
import com.metacto.core.ui.CoreUIConfigs
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import com.metacto.kmm.network.NetworkLogLevel as KmmNetworkLogLevel

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
            networkLogLevel = KmmNetworkLogLevel.ALL,
            shouldShowActualErrorMessages = true,
            errorClass = SerializableNetworkError::class,
            appConfigurationExpirationInMinutes = 1.hours.inWholeMinutes,
        ),
    )

    data object Prod : AppEnvironment(
        coreConfigs = CoreConfigs(
            baseUrl = "https://api.satyadating.com/api",
            storageName = "MetaCtoSampleProd",
            networkLogLevel = KmmNetworkLogLevel.NONE,
            shouldShowActualErrorMessages = false,
            errorClass = SerializableNetworkError::class,
            appConfigurationExpirationInMinutes = 1.days.inWholeMinutes,
        )
    )
}