package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun YoutubePlayer(
    modifier: Modifier = Modifier,
    videoUrl: String? = null,
    videoId: String? = null,
    isPlaying: ((Boolean) -> Unit)? = null,
    isLoading: ((Boolean) -> Unit)? = null,
    onVideoEnded: (() -> Unit)? = null,
)