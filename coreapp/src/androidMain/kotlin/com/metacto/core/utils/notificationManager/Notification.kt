package com.metacto.core.utils.notificationManager

import android.app.PendingIntent
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null,
    @DrawableRes val icon: Int? = null,
    val autoCancel: Boolean = true,
    val channel: NotificationChannel? = null,
    val priority: Int = NotificationCompat.PRIORITY_DEFAULT,
    val defaults: Int = NotificationCompat.DEFAULT_VIBRATE,
    val pendingIntent: PendingIntent? = null
) : CommonParcelable {

    actual companion object {
        actual fun new(
            id: Int?,
            title: String,
            body: String?
        ) = Notification(
            id = id,
            title = title,
            body = body
        )
    }
}