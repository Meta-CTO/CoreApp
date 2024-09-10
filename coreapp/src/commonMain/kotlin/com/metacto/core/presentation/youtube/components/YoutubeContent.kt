package com.metacto.core.presentation.youtube.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.youtubePlayer.YouTubePlayer
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.metacto.core.presentation.youtube.YoutubeContract.Event
import com.metacto.core.presentation.youtube.YoutubeContract.State

@Composable
internal fun YoutubeContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.black)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        // Show back button if is in landscape
        if (state.isLandscape.not()) {
            // Back button
            IconButton(
                onClick = { onEvent(Event.BackClicked) },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = colors.white
                )
            }
        }

        if (state.videoId != null) {
            YouTubePlayer(
                videoId = state.videoId,
                onOrientationChanged = { isLandscape ->
                    onEvent(Event.OrientationChanged(isLandscape = isLandscape))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}