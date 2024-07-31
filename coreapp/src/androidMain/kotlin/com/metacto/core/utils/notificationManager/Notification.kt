package com.metacto.core.utils.notificationManager

import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize
import com.metacto.core.utils.CommonRawValue
import dev.icerock.moko.resources.ImageResource
import kotlinx.parcelize.RawValue

@CommonParcelize
actual data class Notification constructor(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null,
    actual val icon: @CommonRawValue ImageResource? = null,
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
            icon = icon
        )
    }
}