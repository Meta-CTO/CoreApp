package com.metacto.core.notifications

expect class Notification {
    val id: Int?
    val title: String
    val body: String?
    val icon: Int?

    companion object {
        fun new(
            id: Int? = null,
            title: String,
            body: String? = null,
            icon: Int? = null
        ): Notification
    }
}