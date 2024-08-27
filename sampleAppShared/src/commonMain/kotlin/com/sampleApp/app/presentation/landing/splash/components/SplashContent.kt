package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.images.WhiteAppImage
import com.metacto.core.presentation.components.inputFields.PickerInputField
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State

@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    ScreenColumn(
        isRefreshable = false,
        isScrollable = false,
        isRefreshing = false,
        enableSafeInsets = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        topPadding = 4.dp,
        startPadding = 0.dp,
        endPadding = 0.dp
    ) {

        WhiteAppImage(
            url = "https://t4.ftcdn.net/jpg/06/41/kRo33.jpg",
            shimmerLoading = true,
            modifier = Modifier.size(200.dp)
        )

        WhiteAppImage(
            url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQnGKed32c6CGq-N05c05RO0TsKRmSKsLYN1A&s",
            shimmerLoading = true,
            modifier = Modifier.size(200.dp)
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Click Me!",
            onClick = {
                onEvent(Event.ClickMeClicked)
            }
        )

        PickerInputField(
            text = "Hello",
            label = "Test test",
            onClick = {
                onEvent(Event.ClickMeClicked)
            },
            textColor = Color.Red,
            enabled = true,
            placeholder = "Ahmed ahmed",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp)
        )
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Add Calender Event",
            onClick = {
                onEvent(Event.OnCalenderEventClicked)
            }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Red)
        ) {
            if (state.currentVideo != null) {
                VideoPlayer(
                    videoUrl = state.currentVideo.url,
                    videoTitle = state.currentVideo.title,
                    videoArtist = state.currentVideo.artist,
                    videoArtworkUrl = state.currentVideo.artworkUrl,
                    autoPlay = true,
                    scaleToCrop = true,
                    enablePip = true,
                    handleLifecyclePause = false,
                    controllerShowTimeoutMs = 2000,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 1",
                onClick = {
                    onEvent(Event.SetCurrentVideo(videosList()[0]))
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 2",
                onClick = {
                    onEvent(Event.SetCurrentVideo(videosList()[1]))
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 3",
                onClick = {
                    onEvent(Event.SetCurrentVideo(videosList()[2]))
                }
            )
        }
    }
}

private fun videosList() = listOf(
    VideoItemInfo(
        url = "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
        title = "Mahmoud Elshamy - Talking about the future of the world",
        artist = "Mahmoud Elshamy",
        artworkUrl = "https://mahmoudelshamy.com/index-assets/images/profile-2-250x250.png"
    ),
    VideoItemInfo(
        url = "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8",
        title = "Talking about the future of the Compose multiplatform",
        artist = "Ahmed Elzeiny",
        artworkUrl = "https://www.hdwallpapers.in/download/car_in_fire_city_hq-normal.jpg"
    ),
    VideoItemInfo(
        url = "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8?feedId=CA8a1f507a"
    )
)

data class VideoItemInfo(
    val url: String,
    val artist: String? = null,
    val title: String? = null,
    val artworkUrl: String? = null
)