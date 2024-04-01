package com.metacto.core

import com.metacto.strapikmm.datasource.network.NetworkLogLevel

open class CoreEnvironment(
    val title: String,
    val baseUrl: String,
    val networkLogLevel: NetworkLogLevel
)