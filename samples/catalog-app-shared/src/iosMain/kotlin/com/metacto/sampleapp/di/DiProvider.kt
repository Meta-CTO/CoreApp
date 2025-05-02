package com.metacto.sampleapp.di

import com.metacto.core.deepLink.IDeepLinkManager
import com.metacto.core.notifications.INotificationManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DiProvider : KoinComponent {
    val notificationManager by inject<INotificationManager>()
    val deepLinkManager by inject<IDeepLinkManager>()
}