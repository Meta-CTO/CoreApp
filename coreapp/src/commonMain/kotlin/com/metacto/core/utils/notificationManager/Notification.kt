package com.metacto.core.utils.notificationManager

import com.metacto.core.utils.CommonImageResource
import dev.icerock.moko.resources.ImageResource

expect class Notification {
    val id: Int?
    val title: String
    val body: String?
    val icon: CommonImageResource?

    companion object {
        fun new(
            id: Int? = null,
            title: String,
            body: String? = null,
            icon: ImageResource? = null
        ): Notification
    }
}