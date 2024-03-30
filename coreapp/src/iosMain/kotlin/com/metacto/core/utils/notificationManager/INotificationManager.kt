package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.Date

actual interface INotificationManager {
    actual fun schedule(notification: Notification, date: Date)

    actual fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    actual fun scheduleRepeating(notification: Notification, intervalMinutes: Int)

    actual fun cancelScheduled(id: Int)

    actual fun removeDelivered(id: Int)

    actual fun removeAllDelivered()

    actual fun clearBadgeCount()
}