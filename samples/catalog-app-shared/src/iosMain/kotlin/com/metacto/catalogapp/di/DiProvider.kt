package com.metacto.catalogapp.di

import com.metacto.core.deepLink.IDeepLinkManager
import com.metacto.core.notifications.INotificationManager
import com.metacto.kmm.remoteconfig.common.RemoteConfigProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DiProvider : KoinComponent {
    val notificationManager by inject<INotificationManager>()
    val deepLinkManager by inject<IDeepLinkManager>()
    val remoteConfigs by inject<RemoteConfigProvider>()
}