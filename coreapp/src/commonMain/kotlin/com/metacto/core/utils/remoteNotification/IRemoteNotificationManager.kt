package com.metacto.core.utils.remoteNotification

interface IRemoteNotificationManager {
    suspend fun getPushNotificationToken(): String?
    suspend fun deletePushNotificationToken()
    suspend fun subscribeToTopic(topicName: String)
    suspend fun unSubscribeFromTopic(topicName: String)
    fun onNewTokenListener(listener: (String) -> Unit)
    fun onReceiveMessageNotification(listener: (title: String?, body: String?) -> Unit)
    fun onReceiveDataNotification(listener: (payload: NotificationPayload) -> Unit)
    fun onNotificationClicked(listener: (payload: NotificationPayload) -> Unit)
}