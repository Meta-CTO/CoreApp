package com.metacto.core.utils.notificationManager

expect class Notification {
    val id: Int?
    val title: String
    val body: String?

    companion object {
        fun new(
            id: Int? = null,
            title: String,
            body: String? = null
        ): Notification
    }
}