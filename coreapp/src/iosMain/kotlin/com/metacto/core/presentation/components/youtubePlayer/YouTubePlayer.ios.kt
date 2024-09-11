package com.metacto.core.presentation.components.youtubePlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
actual fun YouTubePlayer(
    modifier: Modifier,
    videoId: String,
    onLandscapeMode: (Boolean) -> Unit
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
            autoplay(true)
            controls(true)
            rel(false)
            ivLoadPolicy(false)
            ccLoadPolicy(false)
            fullscreen(true)
        }
    )
}