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
    autoPlay: Boolean = false,
    scaleToCrop: Boolean = false,
    enablePip: Boolean = false,
    onPlayerCreated: ((VideoPlayerController) -> Unit)? = null,
    onFullscreenToggle: (Boolean) -> Unit,
    url: String
)