package com.metacto.core.notifications.di

import com.metacto.core.notifications.INotificationManager
import com.metacto.core.notifications.NotificationManager
import com.metacto.core.notifications.NotificationsConfigs
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.dsl.module
import platform.UserNotifications.UNUserNotificationCenter

internal actual fun platformModule() = module {
    // Add iOS specific dependencies here

    single<NotifierManager> {
        val configs = get<NotificationsConfigs>()
        NotifierManager.apply {
            initialize(
                NotificationPlatformConfiguration.Ios(
                    showPushNotification = configs.showNotifications,
                    askNotificationPermissionOnStart = configs.askNotificationPermissionOnStart
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