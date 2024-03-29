package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.Date

expect interface INotificationManager {
    fun show(notification: Notification)

    fun schedule(notification: Notification, date: Date)

    fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    fun remove(id: Int)

    fun removeAll()

    fun cancelScheduled(id: Int)
}