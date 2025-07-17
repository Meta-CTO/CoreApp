package com.metacto.core

import com.metacto.core.deepLink.DeepLinkParser
import com.metacto.kmm.logger.LogLevel

data class CoreConfigs(
    val storageName: String,
    val baseUrl: String,
    val logLevel: LogLevel,
    val shouldShowActualErrorMessages: Boolean,
    val appConfigurationExpirationInMinutes: Long,
    val deepLinkParsers: Map<String, DeepLinkParser> = emptyMap(),
    val enforceDefaultDataWrapper: Boolean = true,
    val forceUpdateRemoteConfigKey: String? = null,
)