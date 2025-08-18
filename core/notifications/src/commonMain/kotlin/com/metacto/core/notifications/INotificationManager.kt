package com.metacto.core.notifications

import kotlinx.datetime.LocalDateTime

expect interface INotificationManager {
    fun schedule(notification: Notification, date: LocalDateTime)

    fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    fun scheduleRepeating(notification: Notification, intervalMinutes: Int)

    fun cancelScheduled(id: Int)

    fun removeDelivered(id: Int)

    fun removeAllDelivered()

    fun clearBadgeCount()

    suspend fun getPushNotificationToken(): String?

    suspend fun deletePushNotificationToken()

    suspend fun subscribeToTopic(topicName: String)

    suspend fun unSubscribeFromTopic(topicName: String)

    fun onNewTokenListener(listener: (String) -> Unit)

    fun onReceiveMessageNotification(listener: (title: String?, body: String?) -> Unit)

    fun onReceiveDataNotification(listener: (payload: NotificationPayload) -> Unit)

    fun onNotificationClicked(listener: (payload: NotificationPayload) -> Unit)
}