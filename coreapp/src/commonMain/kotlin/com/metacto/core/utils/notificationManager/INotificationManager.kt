package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.Date

expect interface INotificationManager {
    fun schedule(notification: Notification, date: Date)

    fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    fun scheduleRepeating(notification: Notification, intervalMinutes: Int)

    fun cancelScheduled(id: Int)

    fun removeDelivered(id: Int)

    fun removeAllDelivered()

    fun clearBadgeCount()
}