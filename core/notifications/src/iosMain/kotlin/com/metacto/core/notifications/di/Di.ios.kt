package com.metacto.core.notifications.di

import com.metacto.core.notifications.INotificationManager
import com.metacto.core.notifications.NotificationManager
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.dsl.module
import platform.UserNotifications.UNUserNotificationCenter

internal actual fun platformModule(
    androidNotificationIcon: Int?,
    showNotifications: Boolean,
    askNotificationPermissionOnStart: Boolean,
) = module {
    // Add iOS specific dependencies here

    single<NotifierManager> {
        NotifierManager.apply {
            initialize(
                NotificationPlatformConfiguration.Ios(
                    showPushNotification = showNotifications,
                    askNotificationPermissionOnStart = askNotificationPermissionOnStart
                )
            )
        }
    }

    single<INotificationManager> {
        NotificationManager(
            notificationCenter = UNUserNotificationCenter.currentNotificationCenter(),
            notifier = get()
        )
    }
}