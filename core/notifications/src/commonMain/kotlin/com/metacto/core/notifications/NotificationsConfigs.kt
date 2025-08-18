package com.metacto.core.notifications

data class NotificationsConfigs(
    val androidNotificationIcon: Int? = null,
    val showNotifications: Boolean = false,
    val askNotificationPermissionOnStart: Boolean = false,
)
