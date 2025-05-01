package com.metacto.core.ui.components.youtube.player.handler

import com.metacto.core.ui.components.youtube.player.model.YouTubeEvent
import com.metacto.core.ui.components.youtube.player.model.YouTubeOperation

internal object YouTubeActionHandler {

    private val REGEX = "ytplayer://([A-z]+)(\\?data=([A-z\\d.]+))*".toRegex()

    fun handleAction(url: String?): YouTubeEvent? {
        val result = REGEX.matchEntire(url.orEmpty())
        return result?.let { matchResult ->
            val operation = matchResult.groupValues[1].let(YouTubeOperation.Companion::fromStringOrNull)
            val data = matchResult.groupValues[3]
            YouTubeEvent.fromStringOrNull(operation, data)
        }
    }
}
