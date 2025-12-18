package com.metacto.core

import com.metacto.core.deepLink.DeepLinkParser
import com.metacto.kmm.logger.LogLevel
import com.metacto.kmm.network.constants.StrapiVersion
import io.ktor.client.HttpClientConfig

typealias HttpClientConfiguration = HttpClientConfig<*>.() -> Unit

data class CoreConfigs(
    val storageName: String,
    val baseUrl: String,
    val logLevel: LogLevel,
    val shouldShowActualErrorMessages: Boolean,
    val appConfigurationExpirationInMinutes: Long,
    val deepLinkParsers: Map<String, DeepLinkParser> = emptyMap(),
    val strapiVersion: StrapiVersion = StrapiVersion.V5,
    val forceUpdateRemoteConfigKey: String? = null,
    val httpClientConfiguration: HttpClientConfiguration = {},
    val networkUserAgent: String? = null
)