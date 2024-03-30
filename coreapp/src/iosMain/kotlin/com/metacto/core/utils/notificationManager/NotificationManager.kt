package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.Date
import com.metacto.core.utils.toDateComponents
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
    private val notificationCenter: UNUserNotificationCenter
) : INotificationManager {

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

    override fun scheduleRepeating(notification: Notification, intervalMinutes: Int) {
        val timeInterval = intervalMinutes * 60.0
        show(
            notification = notification,
            trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
                timeInterval = timeInterval,
                repeats = true
            )
        )
    }

    private fun show(notification: Notification, trigger: UNNotificationTrigger) {
        val notificationId = notification.id ?: Clock.System.now().toEpochMilliseconds().toInt()

        val notificationContent = UNMutableNotificationContent().apply {
            setTitle(notification.title)
            setSound(UNNotificationSound.defaultSound)
            notification.body?.let {
                setBody(it)
            }
        }
        val notificationRequest = UNNotificationRequest.requestWithIdentifier(
            identifier = notificationId.toString(),
            content = notificationContent,
            trigger = trigger
        )

        notificationCenter.addNotificationRequest(notificationRequest) { error ->
            error?.let { println("Error showing notification: $error") }
        }
    }

    override fun cancelScheduled(id: Int) {
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(
            listOf(id.toString())
        )
    }

    override fun removeDelivered(id: Int) {
        notificationCenter.removeDeliveredNotificationsWithIdentifiers(
            listOf(id.toString())
        )
    }

    override fun removeAllDelivered() {
        notificationCenter.removeAllDeliveredNotifications()
    }

    override fun clearBadgeCount() {
        UIApplication.sharedApplication.applicationIconBadgeNumber = 0
    }
}