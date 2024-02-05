package com.metacto.core.utils.notificationManager

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.icerock.moko.resources.ImageResource

class NotificationManager(
    private val context: Context
) : INotificationManager {

    override fun isPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    override fun createNotificationsChannel(
        id: String,
        name: String,
        priority: Int
    ): NotificationChannel? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return null
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return when {
            service.getNotificationChannel(id) == null -> {
                val channel = NotificationChannel(id, name, priority).apply {
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                }
                service.createNotificationChannel(channel)
                channel
            }

            else -> service.getNotificationChannel(id)
        }
    }

    override fun showNotification(
        notificationId: Int?,
        title: String,
        info: String,
        icon: ImageResource,
        autoCancel: Boolean,
        channelId: String,
        priority: Int,
        defaults: Int,
        pendingIntent: PendingIntent?
    ) {
        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val validNotificationId = notificationId ?: System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon.drawableResId)
            .setContentTitle(title)
            .setContentText(info)
            .setAutoCancel(autoCancel)
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setDefaults(defaults)
            .build()

        notificationManager.notify(validNotificationId, notification)
    }

    override fun dismissNotification(id: Int) {
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.cancel(id)
    }
}