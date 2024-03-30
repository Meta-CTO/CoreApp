package com.metacto.core.utils.notificationManager

import android.app.NotificationChannel
import android.app.NotificationManager
import com.metacto.core.utils.Date

actual interface INotificationManager {
    fun show(notification: Notification)

    fun createNotificationsChannel(
        id: String,
        name: String,
        priority: Int = NotificationManager.IMPORTANCE_HIGH
    ): NotificationChannel?

    actual fun schedule(notification: Notification, date: Date)

    actual fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    actual fun scheduleRepeating(notification: Notification, intervalMinutes: Int)

    actual fun cancelScheduled(id: Int)

    actual fun removeDelivered(id: Int)

    actual fun removeAllDelivered()

    actual fun clearBadgeCount()

    companion object {
        const val APP_CHANNEL_ID = "AppNotifications"
        const val APP_CHANNEL_Name = "App Notifications"
    }
}