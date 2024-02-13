package com.metacto.core.utils.pushNotifications

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.metacto.core.utils.extensions.cancelIfActive
import com.swensonhe.strapikmm.util.exceptionIfActive
import com.swensonhe.strapikmm.util.resumeIfActive
import kotlinx.coroutines.suspendCancellableCoroutine

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

    override suspend fun forceGetPushToken(): String = suspendCancellableCoroutine { cont ->
        if (pushToken != null) {
            cont.resumeIfActive(pushToken.orEmpty())
        } else {
            firebaseMessaging.token
                .addOnSuccessListener {
                    pushToken = it
                    cont.resumeIfActive(it)
                }
                .addOnFailureListener {
                    cont.exceptionIfActive(it)
                }
                .addOnCanceledListener {
                    cont.cancelIfActive()
                }
        }
    }

    override fun onPushTokenUpdated(newToken: String) {
        pushToken = newToken
    }
}