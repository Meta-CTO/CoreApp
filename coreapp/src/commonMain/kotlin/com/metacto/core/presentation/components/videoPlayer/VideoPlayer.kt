package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface VideoPlayerController {
    fun play() {}
    fun pause() {}
}

private const val DEFAULT_ID = "default_player_id"

@Composable
expect fun VideoPlayer(
    modifier: Modifier = Modifier,
    playerId: String = DEFAULT_ID,
    videoUrl: String,
    videoArtist: String? = null,
    videoTitle: String? = null,
    videoArtworkUrl: String? = null,
    autoPlay: Boolean = false,
    scaleToCrop: Boolean = false,
    enablePip: Boolean = false,
    handleLifecyclePause: Boolean = true,
    controllerShowTimeoutMs: Int = 0,
    onPlayerCreated: ((VideoPlayerController) -> Unit)? = null
)