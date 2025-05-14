package com.metacto.core

import com.metacto.core.deepLink.DeepLinkParser
import com.metacto.kmm.logger.LogLevel
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import kotlin.reflect.KClass

data class CoreConfigs(
    val storageName: String,
    val baseUrl: String,
    val logLevel: LogLevel,
    val shouldShowActualErrorMessages: Boolean,
    val errorClass: KClass<SerializableNetworkError>, // TODO: Revisit that
    val appConfigurationExpirationInMinutes: Long,
    val deepLinkParsers: Map<String, DeepLinkParser> = emptyMap()
)