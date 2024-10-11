package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface VideoPlayerController {
    fun play() {}
    fun pause() {}
}

@Composable
expect fun VideoPlayer(
    modifier: Modifier = Modifier,
    uniqueId: String = "default_player_id",
    videoUrl: String,
    videoArtist: String? = null,
    videoTitle: String? = null,
    videoArtworkUrl: String? = null,
    autoPlay: Boolean = false,
    scaleToCrop: Boolean = false,
    enablePip: Boolean = false,
    enableMediaMetadata: Boolean = true,
    handleLifecyclePause: Boolean = true,
    controllerShowTimeoutMs: Int = 0,
    showControls: Boolean = true,
    onPlayerCreated: ((VideoPlayerController) -> Unit)? = null
)