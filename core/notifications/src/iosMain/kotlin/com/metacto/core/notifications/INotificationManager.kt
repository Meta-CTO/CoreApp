package com.metacto.core.notifications

import kotlinx.datetime.LocalDateTime

val Class = INotificationManager::class

actual interface INotificationManager {

    fun onApplicationDidReceiveRemoteNotification(userInfo: Map<Any?, *>)

    actual fun schedule(notification: Notification, date: LocalDateTime)

    actual fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    actual fun scheduleRepeating(notification: Notification, intervalMinutes: Int)

    actual fun cancelScheduled(id: Int)

    actual fun removeDelivered(id: Int)

    actual fun removeAllDelivered()

    actual fun clearBadgeCount()

    actual suspend fun getPushNotificationToken(): String?

    actual suspend fun deletePushNotificationToken()

    actual suspend fun subscribeToTopic(topicName: String)

    actual suspend fun unSubscribeFromTopic(topicName: String)

    actual fun onNewTokenListener(listener: (String) -> Unit)

    actual fun onReceiveMessageNotification(listener: (title: String?, body: String?) -> Unit)

    actual fun onReceiveDataNotification(listener: (payload: NotificationPayload) -> Unit)

    actual fun onNotificationClicked(listener: (payload: NotificationPayload) -> Unit)
}