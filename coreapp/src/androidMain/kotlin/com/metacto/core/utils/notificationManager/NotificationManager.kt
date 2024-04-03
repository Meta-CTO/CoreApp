package com.metacto.core.utils.notificationManager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.metacto.core.utils.Date
import com.metacto.core.utils.extensions.getAppIconResId
import com.metacto.core.utils.extensions.getLauncherPendingIntent
import com.metacto.coreApp.MR
import kotlinx.datetime.Clock
import java.util.Calendar
import java.util.concurrent.TimeUnit
import android.app.Notification as PlatformNotification


class NotificationManager(private val context: Context) : INotificationManager {

    private val notificationService = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager

    private val alarmService = context.getSystemService(
        Context.ALARM_SERVICE
    ) as AlarmManager

    override fun show(notification: Notification) {
        // Create the notification channel if required
        val channelId = notification.channel?.id ?: INotificationManager.APP_CHANNEL_ID
        val channelName = notification.channel?.name ?: INotificationManager.APP_CHANNEL_Name
        createNotificationsChannel(
            id = channelId,
            name = channelName
        )

        // Prepare the notification data
        val notificationId = notification.id ?: Clock.System.now().toEpochMilliseconds().toInt()
        val pendingIntent = notification.pendingIntent ?: context.getLauncherPendingIntent()
        val notificationIcon = notification.icon?.drawableResId
            ?: context.getAppIconResId()
            ?: MR.images.ic_default_notifications_icon.drawableResId

        // Create the notification builder
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setSmallIcon(notificationIcon)
            .setAutoCancel(notification.autoCancel)
            .setPriority(notification.priority)
            .setContentIntent(pendingIntent)
            .setDefaults(notification.defaults)

        // Then show the notification
        notificationService.notify(notificationId, builder.build())
    }

    override fun createNotificationsChannel(
        id: String,
        name: String,
        priority: Int
    ): NotificationChannel? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return null
        return when {
            notificationService.getNotificationChannel(id) == null -> {
                val channel = NotificationChannel(id, name, priority).apply {
                    lockscreenVisibility = PlatformNotification.VISIBILITY_PUBLIC
                }
                notificationService.createNotificationChannel(channel)
                channel
            }

            else -> notificationService.getNotificationChannel(id)
        }
    }

    override fun schedule(notification: Notification, date: Date) {
        // Prepare the notification id
        val notificationId = notification.id ?: Clock.System.now().toEpochMilliseconds().toInt()

        // Create the receiver intent
        val intent = Intent(context, NotificationsReceiver::class.java).apply {
            putExtra(NotificationsReceiver.KEY_NOTIFICATION, notification)
        }

        // Create the pending intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Start the alarm
        alarmService.set(AlarmManager.RTC_WAKEUP, date.time, pendingIntent)
    }

    override fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int) {
        // Prepare date and repeat interval
        val date = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        val repeatInterval = TimeUnit.DAYS.toMillis(1) // Repeated every day

        // Then schedule
        scheduleRepeating(
            notification = notification,
            triggerDate = date,
            repeatIntervalMillis = repeatInterval
        )
    }

    override fun scheduleRepeating(notification: Notification, intervalMinutes: Int) {
        // Prepare date and repeat interval
        val date = Calendar.getInstance().apply {
            add(Calendar.MINUTE, intervalMinutes)
        }
        val repeatIntervalMillis = TimeUnit.MINUTES.toMillis(intervalMinutes.toLong())

        // Then schedule
        scheduleRepeating(
            notification = notification,
            triggerDate = date,
            repeatIntervalMillis = repeatIntervalMillis
        )
    }

    private fun scheduleRepeating(
        notification: Notification,
        triggerDate: Calendar,
        repeatIntervalMillis: Long
    ) {
        // Prepare the notification id
        val notificationId = notification.id ?: Clock.System.now().toEpochMilliseconds().toInt()

        // Create the receiver intent
        val intent = Intent(context, NotificationsReceiver::class.java).apply {
            putExtra(NotificationsReceiver.KEY_NOTIFICATION, notification)
        }

        // Create the pending intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Start the alarm
        alarmService.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerDate.timeInMillis,
            repeatIntervalMillis,
            pendingIntent
        )
    }

    override fun cancelScheduled(id: Int) {
        // Create the receiver intent
        val intent = Intent(context, NotificationsReceiver::class.java)

        // Create the pending intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )

        // Then cancel
        alarmService.cancel(pendingIntent)
    }

    override fun removeDelivered(id: Int) {
        notificationService.cancel(id)
    }

    override fun removeAllDelivered() {
        notificationService.cancelAll()
    }

    override fun clearBadgeCount() {
        // In Android to clear badge count you have to clear all notifications in the notification center
        notificationService.cancelAll()
    }
}