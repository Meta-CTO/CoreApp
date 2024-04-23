package com.metacto.core.utils.notificationManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.metacto.core.utils.extensions.getSerializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationsReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationManager by inject<INotificationManager>()

    override fun onReceive(context: Context, intentData: Intent?) {
        intentData?.getSerializable<Notification>(KEY_NOTIFICATION)?.let {
            notificationManager.show(it)
        }
    }

    companion object {
        const val KEY_NOTIFICATION = "notification"
    }
}