package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.Date
import com.metacto.core.utils.extensions.orZero
import com.metacto.core.utils.toDateComponents
import com.swensonhe.strapikmm.util.Logger
import kotlinx.datetime.Clock
import platform.Foundation.NSDateComponents
import platform.UIKit.UIApplication
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNNotificationTrigger
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

class NotificationManager(
    private val notificationCenter: UNUserNotificationCenter,
) : INotificationManager {

    private val logger = Logger("NotificationManager")

    override fun show(notification: Notification) {
        show(
            notification = notification,
            trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(1.0, false)
        )
    }

    private fun show(notification: Notification, trigger: UNNotificationTrigger) {
        val notificationId = notification.id ?: Clock.System.now().toEpochMilliseconds().toInt()

        val notificationContent = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
            setSound(UNNotificationSound.defaultSound)
        }
        val notificationRequest = UNNotificationRequest.requestWithIdentifier(
            identifier = notificationId.toString(),
            content = notificationContent,
            trigger = trigger
        )

        notificationCenter.addNotificationRequest(notificationRequest) { error ->
            error?.let { logger.log("Error showing notification: $error") }
        }
    }

    override fun schedule(notification: Notification, date: Date) {
        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
            dateComponents = date.toDateComponents(),
            repeats = false
        )

        show(
            notification = notification,
            trigger = trigger
        )
    }

    override fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int) {
        val dateComponents = NSDateComponents().apply {
            setHour(hourOfDay.toLong())
            setMinute(minute.toLong())
        }

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
            dateComponents = dateComponents,
            repeats = true
        )

        show(
            notification = notification,
            trigger = trigger
        )
    }

    override fun remove(id: Int) {
        // Prepare new badge number
        val currentBadgeNumber = UIApplication.sharedApplication.applicationIconBadgeNumber()
        val newBadgeNumber = currentBadgeNumber.dec()
            .takeIf { it > 0 }
            .orZero()

        // Remove the notification
        notificationCenter.removeDeliveredNotificationsWithIdentifiers(
            listOf(id.toString())
        )

        // And update badge number
        UIApplication.sharedApplication.applicationIconBadgeNumber = newBadgeNumber
    }

    override fun removeAll() {
        notificationCenter.removeAllDeliveredNotifications()
        UIApplication.sharedApplication.applicationIconBadgeNumber = 0
    }

    override fun cancelScheduled(id: Int) {
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(listOf(id.toString()))
    }
}