package com.metacto.core.utils.pushNotifications

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

class FirebasePushNotificationsManager constructor(
    private val firebaseMessaging: FirebaseMessaging
) : IPushNotificationsManager {
    private var pushToken: String? = null

    init {
        loadPushToken()
    }

    private fun loadPushToken() {
        firebaseMessaging.token.addOnCompleteListener { task: Task<String?> ->
            if (task.isSuccessful) {
                pushToken = task.result
            }
        }
    }

    override fun getPushToken(): String? {
        return pushToken
    }

    override fun onPushTokenUpdated(newToken: String) {
        pushToken = newToken
    }
}