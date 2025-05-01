package com.metacto.core.ui.components.youtube.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
actual fun YouTubePlayer(
    modifier: Modifier,
    videoId: String,
    onLandscapeMode: (Boolean) -> Unit,
    shouldAutoPlay: Boolean,
    showControls: Boolean,
    showFullScreenButton: Boolean
) {
    val hostState = remember { YouTubePlayerHostState() }
    val coroutineScope = rememberCoroutineScope()

    if (hostState.currentState == YouTubePlayerState.Ready) {
        coroutineScope.launch {
            hostState.loadVideo(YouTubeVideoId(videoId))
        }
    }

    CommonYouTubePlayer(
        modifier = modifier,
        hostState = hostState,
        options = SimpleYouTubePlayerOptionsBuilder.builder {
            autoplay(shouldAutoPlay)
            controls(showControls)
            rel(false)
            ivLoadPolicy(false)
            ccLoadPolicy(false)
            fullscreen(showFullScreenButton)
        }
    )
}