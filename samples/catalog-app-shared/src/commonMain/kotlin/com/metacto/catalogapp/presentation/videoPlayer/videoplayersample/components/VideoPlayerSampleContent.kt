package com.metacto.catalogapp.presentation.videoPlayer.videoplayersample.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.catalogapp.presentation.videoPlayer.videoplayersample.VideoPlayerSampleContract.Event
import com.metacto.catalogapp.presentation.videoPlayer.videoplayersample.VideoPlayerSampleContract.State
import com.metacto.core.ui.components.inputFields.PrimaryTextInputField
import com.metacto.core.ui.mediaplayers.videoPlayer.VideoPlayer
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun VideoPlayerSampleContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // test video url
    // http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4

    // Di
    val navManager = koinInject<NavManager>()

    // states
    var videoUrl by remember { mutableStateOf("") }

    // Container column
    AppScreenColumn(
        title = "VideoPlayerSample",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        // Video url input field
        PrimaryTextInputField(
            text = videoUrl,
            label = "Video Url",
            onValueChange = {
                videoUrl = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Video Player
        if (videoUrl.isNotEmpty()) {
            VideoPlayer(
                videoUrl = videoUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacings.spacing500)
                    .padding(top = spacings.spacing32)
            )
        }
    }
}
