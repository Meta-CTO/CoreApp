package com.metacto.core.remoteNotification

import com.mmk.kmpnotifier.notification.PayloadData

interface IRemoteNotificationManager {

    suspend fun getPushNotificationToken(): String?
    suspend fun deletePushNotificationToken()
    suspend fun subscribeToTopic(topicName: String)
    suspend fun unSubscribeFromTopic(topicName: String)
    fun addNewTokenListener(onNewToken: (String) -> Unit)
    fun onReceiveNewNotification(onNewNotification: (title: String?, body: String?) -> Unit)
    fun onReceiveNewNotification(onNewNotification: (data: PayloadData) -> Unit)

}