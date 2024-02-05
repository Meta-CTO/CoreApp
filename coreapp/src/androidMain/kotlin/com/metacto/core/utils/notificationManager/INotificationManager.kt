package com.metacto.core.utils.notificationManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import dev.icerock.moko.resources.ImageResource

interface INotificationManager {
    fun isPermissionGranted(): Boolean

    fun createNotificationsChannel(
        id: String,
        name: String,
        priority: Int = NotificationManager.IMPORTANCE_HIGH
    ): NotificationChannel?

    fun showNotification(
        notificationId: Int? = null,
        title: String,
        info: String,
        icon: ImageResource,
        autoCancel: Boolean = true,
        channelId: String,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        defaults: Int = NotificationCompat.DEFAULT_VIBRATE,
        pendingIntent: PendingIntent? = null
    )

    fun dismissNotification(id: Int)
}