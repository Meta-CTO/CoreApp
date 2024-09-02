package com.metacto.core

import com.metacto.core.utils.CommonImageResource
import com.metacto.strapikmm.datasource.network.NetworkLogLevel

open class CoreEnvironment(
    open val title: String,
    open val baseUrl: String,
    open val networkLogLevel: NetworkLogLevel,
    open val iosAppStoreId: String,
    open val forceUpdateRemoteConfigKey: String? = null,
    open val currentAppConfigurationVersion: Int,
    open val appConfigurationExpirationInMinutes: Long,
    open val remoteNotificationIcon: CommonImageResource? = null,
    open val showRemoteNotifications: Boolean = false,
    open val askRemoteNotificationPermissionOnStart: Boolean = false,
)