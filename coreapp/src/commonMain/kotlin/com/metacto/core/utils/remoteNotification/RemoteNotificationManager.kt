package com.metacto.core.utils.remoteNotification

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData

class RemoteNotificationManager(
    private val notifier: NotifierManager
) : IRemoteNotificationManager, NotifierManager.Listener {

    private var onNewTokenListener: ((String) -> Unit)? = null
    private var onMessageNotificationListener: ((String?, String?) -> Unit)? = null
    private var onDataNotificationListener: ((NotificationPayload) -> Unit)? = null
    private var onNotificationClickedListener: ((NotificationPayload) -> Unit)? = null

    init {
        notifier.addListener(this)
    }

    override suspend fun getPushNotificationToken(): String? {
        return notifier.getPushNotifier().getToken()
    }

    override suspend fun deletePushNotificationToken() {
        notifier.getPushNotifier().deleteMyToken()
    }

    override suspend fun subscribeToTopic(topicName: String) {
        notifier.getPushNotifier().subscribeToTopic(topicName)
    }

    override suspend fun unSubscribeFromTopic(topicName: String) {
        notifier.getPushNotifier().unSubscribeFromTopic(topicName)
    }

    override fun onNewTokenListener(listener: (String) -> Unit) {
        onNewTokenListener = listener
    }

    override fun onReceiveMessageNotification(listener: (title: String?, body: String?) -> Unit) {
        onMessageNotificationListener = listener
    }

    override fun onReceiveDataNotification(listener: (data: NotificationPayload) -> Unit) {
        onDataNotificationListener = listener
    }

    override fun onNotificationClicked(listener: (payload: NotificationPayload) -> Unit) {
        onNotificationClickedListener = listener
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        onNewTokenListener?.invoke(token)
    }

    override fun onPushNotification(title: String?, body: String?) {
        super.onPushNotification(title, body)
        onMessageNotificationListener?.invoke(title, body)
    }

    override fun onPayloadData(data: PayloadData) {
        super.onPayloadData(data)
        onDataNotificationListener?.invoke(data)
    }

    override fun onNotificationClicked(data: PayloadData) {
        super.onNotificationClicked(data)
        onNotificationClickedListener?.invoke(data)
    }
}