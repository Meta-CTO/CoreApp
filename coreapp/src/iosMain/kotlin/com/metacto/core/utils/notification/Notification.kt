package com.metacto.core.utils.notification

actual data class Notification(
    actual val id: Int? = null,
    actual val title: String,
    actual val body: String? = null,
    actual val icon: Int? = null
) {

    actual companion object {
        actual fun new(
            id: Int?,
            title: String,
            body: String?,
            icon: Int?,
        ) = Notification(
            id = id,
            title = title,
            body = body,
            icon = icon
        )
    }
}