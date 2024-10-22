package com.metacto.core.utils.notificationManager

actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null
) {

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