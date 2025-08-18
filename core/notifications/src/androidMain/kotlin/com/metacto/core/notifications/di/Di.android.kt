package com.metacto.core.notifications.di

import com.metacto.core.notifications.INotificationManager
import com.metacto.core.notifications.NotificationManager
import com.metacto.core.notifications.NotificationsConfigs
import com.metacto.core.notifications.R
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual fun platformModule() = module {
    // Add Android specific dependencies here
    single<NotifierManager> {
        val configs = get<NotificationsConfigs>()
        NotifierManager.apply {
            initialize(
                configuration = NotificationPlatformConfiguration.Android(
                    showPushNotification = configs.showNotifications,
                    notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData(),
                    notificationIconResId = configs.androidNotificationIcon ?: R.drawable.ic_default_notifications_icon,
                )
            )
        }
    }

    single<INotificationManager> {
        NotificationManager(
            context = androidContext(),
            notifier = get()
        )
    }
}