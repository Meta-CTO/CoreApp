package com.metacto.core.notifications.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun notificationsModule(
    androidNotificationIcon: Int? = null,
    showNotifications: Boolean = false,
    askNotificationPermissionOnStart: Boolean = false,
) = module {
    // Common dependencies can be added here
    includes(
        platformModule(
            androidNotificationIcon = androidNotificationIcon,
            showNotifications = showNotifications,
            askNotificationPermissionOnStart = askNotificationPermissionOnStart,
        )
    )
}

internal expect fun platformModule(
    androidNotificationIcon: Int?,
    showNotifications: Boolean,
    askNotificationPermissionOnStart: Boolean,
): Module