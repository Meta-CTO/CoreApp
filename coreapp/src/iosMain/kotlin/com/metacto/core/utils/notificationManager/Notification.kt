package com.metacto.core.utils.notificationManager

import dev.icerock.moko.resources.ImageResource

actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val description: String? = null,
    actual val icon: ImageResource? = null
) {

    actual companion object {
        actual fun new(
            id: Int?,
            title: String,
            description: String?,
            icon: ImageResource?
        ) = Notification(
            id = id,
            title = title,
            description = description,
            icon = icon
        )
    }
}