package com.metacto.core

import com.metacto.kmm.network.NetworkLogLevel

data class CoreConfigs(
    val storageName: String,
    val baseUrl: String,
    val networkLogLevel: NetworkLogLevel = NetworkLogLevel.ALL
)
