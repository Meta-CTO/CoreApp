package com.sampleApp.app.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.metacto.core.utils.extensions.toFeetInches
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
        isScrollable = false
    ) {
        VideoPlayer(
            uniqueId = "profile_video_player",
            videoUrl = videoInfo.url,
            videoTitle = videoInfo.title,
            videoArtist = videoInfo.artist,
            videoArtworkUrl = videoInfo.artworkUrl,
            autoPlay = false,
            scaleToCrop = true,
            enablePip = true,
            handleLifecyclePause = false,
            controllerShowTimeoutMs = 2000,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Text(
            text = 20f.toFeetInches()
        )

//        AudioPlayer(
//            modifier = Modifier.fillMaxWidth(),
//            audioUrl = "https://commondatastorage.googleapis.com/codeskulptor-assets/Evillaugh.ogg",
////            audioUrl = "https://actions.google.com/sounds/v1/alarms/beep_short.ogg"
//        )

        PrimaryFilledButton(
            text = "Open native picker",
            onClick = {
                onEvent(Event.NativeItemPicker)
            }
        )
    }
}
