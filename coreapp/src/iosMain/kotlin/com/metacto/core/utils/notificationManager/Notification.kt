package com.metacto.core.utils.notificationManager

import dev.icerock.moko.resources.ImageResource

actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null,
    actual val icon: ImageResource? = null
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