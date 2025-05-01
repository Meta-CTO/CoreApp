package com.metacto.core

import com.metacto.core.deepLink.DeepLinkParser
import com.metacto.kmm.network.NetworkLogLevel
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import kotlin.reflect.KClass

data class CoreConfigs(
    val storageName: String,
    val baseUrl: String,
    val networkLogLevel: NetworkLogLevel,
    val shouldShowActualErrorMessages: Boolean,
    val errorClass: KClass<SerializableNetworkError>,
    val deepLinkParsers: Map<String, DeepLinkParser> = emptyMap()
)