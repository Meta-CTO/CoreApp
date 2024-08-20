package com.metacto.core

import com.metacto.strapikmm.datasource.network.NetworkLogLevel

open class CoreEnvironment(
    open val title: String,
    open val baseUrl: String,
    open val networkLogLevel: NetworkLogLevel,
    open val storeAppId: String,
    open val forceUpdateRemoteConfigKey: String,
)