package com.metacto.core.utils.notificationManager

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

    actual fun removeScheduled(id: Int)
}