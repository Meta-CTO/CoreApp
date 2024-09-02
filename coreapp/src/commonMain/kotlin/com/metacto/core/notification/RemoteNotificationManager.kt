package com.metacto.core.notification

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData

class RemoteNotificationManager() : IRemoteNotificationManager {
    override suspend fun getPushNotificationToken() =
        NotifierManager.getPushNotifier().getToken()


    override suspend fun deletePushNotificationToken() {
        NotifierManager.getPushNotifier().deleteMyToken()
    }

    override suspend fun subscribeToTopic(topicName: String) {
        NotifierManager.getPushNotifier().subscribeToTopic(topicName)
    }

    override suspend fun unSubscribeFromTopic(topicName: String) {
        NotifierManager.getPushNotifier().unSubscribeFromTopic(topicName)
    }

    override fun addNewTokenListener(onNewToken: (String) -> Unit) {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                onNewToken(token)
            }
        })
    }

    override fun onReceiveNewNotification(onNewNotification: (title: String?, body: String?) -> Unit) {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onPushNotification(title: String?, body: String?) {
                onNewNotification(title, body)
            }
        })
    }

    override fun onReceiveNewNotification(onNewNotification: (data: PayloadData) -> Unit) {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onPayloadData(data: PayloadData) {
                onNewNotification(data)
            }
        })
    }
}