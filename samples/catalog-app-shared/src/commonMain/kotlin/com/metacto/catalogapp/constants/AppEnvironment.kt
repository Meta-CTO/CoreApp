package com.metacto.catalogapp.constants

import com.metacto.catalogapp.deepLinks.DEEP_LINK_PARSERS
import com.metacto.core.CoreConfigs
import com.metacto.core.ui.CoreUIConfigs
import com.metacto.kmm.logger.LogLevel
import sp.bvantur.inspektify.ktor.AutoDetectTarget
import sp.bvantur.inspektify.ktor.DataRetentionPolicy
import sp.bvantur.inspektify.ktor.InspektifyKtor
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
            appConfigurationExpirationInMinutes = 1.hours.inWholeMinutes,
            deepLinkParsers = DEEP_LINK_PARSERS,
            httpClientConfiguration = {
                install(InspektifyKtor) {
                    title = "CatalogApp (DEV)"
                    shortcutEnabled = true
                    dataRetentionPolicy = DataRetentionPolicy.DayDuration(15)
                    autoDetectEnabledFor = setOf(AutoDetectTarget.Android, AutoDetectTarget.Apple)
                }
            }
        ),
    )

    data object Prod : AppEnvironment(
        coreConfigs = CoreConfigs(
            baseUrl = "https://api.satyadating.com/api",
            storageName = "MetaCtoSampleProd",
            logLevel = LogLevel.NONE,
            shouldShowActualErrorMessages = false,
            appConfigurationExpirationInMinutes = 1.days.inWholeMinutes,
            deepLinkParsers = DEEP_LINK_PARSERS,
            httpClientConfiguration = {
                install(InspektifyKtor) {
                    title = "CatalogApp"
                    shortcutEnabled = false
                    dataRetentionPolicy = DataRetentionPolicy.DayDuration(1)
                    autoDetectEnabledFor = setOf(AutoDetectTarget.Android, AutoDetectTarget.Apple)
                }
            }
        )
    )
}