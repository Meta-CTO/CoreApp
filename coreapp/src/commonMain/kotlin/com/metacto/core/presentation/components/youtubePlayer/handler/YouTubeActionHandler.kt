package com.metacto.core.presentation.components.youtubePlayer.handler

import com.metacto.core.presentation.components.youtubePlayer.model.YouTubeEvent
import com.metacto.core.presentation.components.youtubePlayer.model.YouTubeOperation
import com.metacto.strapikmm.util.Logger

object YouTubeActionHandler {

    private val REGEX = "ytplayer://([A-z]+)(\\?data=([A-z\\d.]+))*".toRegex()
    private val logger = Logger("YouTubeActionHandler")

    fun handleAction(url: String?): YouTubeEvent? {
        val result = REGEX.matchEntire(url.orEmpty())
        return result?.let { matchResult ->
            val operation = matchResult.groupValues[1].let(YouTubeOperation.Companion::fromStringOrNull)
            val data = matchResult.groupValues[3]
            logger.log("webViewState. OPERATION_HANDLED: Path: $url " +
                    "Operation: $operation Data: $data")
            YouTubeEvent.fromStringOrNull(operation, data)
        }
    }
}
