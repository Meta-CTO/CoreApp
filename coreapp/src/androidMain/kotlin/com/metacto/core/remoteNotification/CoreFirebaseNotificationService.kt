package com.metacto.core.remoteNotification

import com.google.firebase.messaging.FirebaseMessagingService
import com.metacto.core.utils.pushNotifications.IPushNotificationsManager
import org.koin.android.ext.android.inject

abstract class CoreFirebaseNotificationService : FirebaseMessagingService() {
    protected val pushNotificationsManager by inject<IPushNotificationsManager>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        pushNotificationsManager.onPushTokenUpdated(token)
    }
}