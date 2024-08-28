package com.sampleApp.app.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.sampleApp.app.presentation.models.VideoItemInfo
import com.sampleApp.app.presentation.profile.ProfileContract.Event
import com.sampleApp.app.presentation.profile.ProfileContract.State

@Composable
internal fun ProfileContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val videoInfo = remember {
        VideoItemInfo(
            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            title = "Random Video Title",
            artist = "Random Artist",
            artworkUrl = "https://yurielkaim.com/wp-content/uploads/2016/03/Happiness-Habits-10-Things-Happy-People-Do-Before-Bed-1200x900.jpg"
        )
    }

    // Container column
    ScreenColumn(
        isScrollable = true
    ) {
        VideoPlayer(
            videoUrl = videoInfo.url,
            videoTitle = videoInfo.title,
            videoArtist = videoInfo.artist,
            videoArtworkUrl = videoInfo.artworkUrl,
            autoPlay = true,
            scaleToCrop = true,
            enablePip = true,
            handleLifecyclePause = false,
            controllerShowTimeoutMs = 2000,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}
