package com.sampleApp.app.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metacto.core.ui.mediaplayers.videoPlayer.VideoPlayer
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.images.AppImage
import com.metacto.core.utils.extensions.toFeetInches
import com.sampleApp.app.presentation.models.VideoItemInfo
import com.sampleApp.app.presentation.profile.ProfileContract.Event
import com.sampleApp.app.presentation.profile.ProfileContract.State
import com.sampleApp.app.resources.FileResources
import com.sampleApp.app.resources.Res
import com.sampleApp.app.resources.file

@Composable
internal fun ProfileContent(
    state: State,
    onEvent: (Event) -> Unit,
) {
    val videoInfo = remember {
        VideoItemInfo(
            url = "https://storage.sardius.media/-KrXWhrxRAYPfu44QPJ0/archives/DAA6A5576Dd5Ee41CBd6B68696F6/media/playlist.m3u8?feedId=27d824FCdF&vttUrl=https%253A%252F%252Fstorage.sardius.media%252F-KrXWhrxRAYPfu44QPJ0%252Farchives%252FDAA6A5576Dd5Ee41CBd6B68696F6%252Fstatic%252F1730996105358-1.vtt",
//            url = FileResources.intro_video.getUri(),
            title = "Random Video Title",
            artist = "Random Artist",
            artworkUrl = "https://yurielkaim.com/wp-content/uploads/2016/03/Happiness-Habits-10-Things-Happy-People-Do-Before-Bed-1200x900.jpg"
        )
    }
    // Container column
    ScreenColumn(
        startPadding = 0.dp,
        endPadding = 0.dp,
        isScrollable = false
    ) {
        VideoPlayer(
//            uniqueId = "profile_video_player",
//            videoUrl = Res.file.intro_video.getUri(),
//            autoPlay = true,
//            scaleToCrop = true,
//            enableVoice = false,
//            enablePip = false,
//            enableMediaMetadata = false,
//            autoRepeat = true,
//            controlsType = ControlsType.HideControls,
//            onVideoLoop = {
//                println("Video looped")
//            },
//            onVideoEnd = {
//                println("Video ended")
//            },
            uniqueId = "profile_video_player",
            videoUrl = videoInfo.url,
            videoTitle = videoInfo.title,
            videoArtist = videoInfo.artist,
            videoArtworkUrl = videoInfo.artworkUrl,
            autoPlay = false,
            scaleToCrop = false,
            enablePip = true,
            handleLifecyclePause = false,
            controllerShowTimeoutMs = 2000,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )


        AppImage(
            url = "https://storage.googleapis.com/journey-rewards-uploads-us-east1/image_NIHI%20Resorts-white-logo.svg",
            modifier = Modifier
                .size(100.dp)
                .background(Color.Black)
        )


        AppImage(
            url = "https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D",
            modifier = Modifier
                .size(100.dp)
                .background(Color.Black)
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
