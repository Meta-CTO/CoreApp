package com.sampleApp.app.presentation.landing.youtube.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.youtubePlayer.YouTubePlayer
import com.sampleApp.app.presentation.landing.youtube.YoutubeContract.Event
import com.sampleApp.app.presentation.landing.youtube.YoutubeContract.State

@Composable
internal fun YoutubeContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        YouTubePlayer(
            videoId = "Gmhk7mWG050",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}