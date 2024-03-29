package com.metacto.core.utils.notificationManager

import android.app.NotificationChannel
import android.app.NotificationManager
import com.metacto.core.utils.Date

actual interface INotificationManager {
    actual fun show(notification: Notification)

    actual fun schedule(
        notification: Notification,
        date: Date
    )

    actual fun scheduleRepeating(
        notification: Notification,
        hourOfDay: Int,
        minute: Int
    )

    actual fun remove(id: Int)

    actual fun removeAll()

    actual fun cancelScheduled(id: Int)

    fun createNotificationsChannel(
        id: String,
        name: String,
        priority: Int = NotificationManager.IMPORTANCE_HIGH
    ): NotificationChannel?

    companion object {
        const val APP_CHANNEL_ID = "AppNotifications"
        const val APP_CHANNEL_Name = "App Notifications"
    }
}