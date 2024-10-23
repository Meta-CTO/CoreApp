package com.metacto.core.presentation.components.youtubePlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun YouTubePlayer(
    modifier: Modifier = Modifier,
    videoId: String,
    onLandscapeMode: (Boolean) -> Unit,
    shouldAutoPlay: Boolean = true,
    showControls: Boolean = true,
    showFullScreenButton: Boolean = true,
)