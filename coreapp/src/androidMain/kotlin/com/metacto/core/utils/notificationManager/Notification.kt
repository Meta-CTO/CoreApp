package com.metacto.core.utils.notificationManager

import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize
import dev.icerock.moko.resources.ImageResource

@CommonParcelize
actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null,
    actual val icon: CommonImageResource? = null,
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
            body: String?,
            icon: ImageResource?
        ) = Notification(
            id = id,
            title = title,
            body = body,
            icon = icon?.drawableResId
        )
    }
}