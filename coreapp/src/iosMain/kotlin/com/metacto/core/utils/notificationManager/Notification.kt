package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.CommonImageResource
import dev.icerock.moko.resources.ImageResource

actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null,
    actual val icon: CommonImageResource? = null
) {

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