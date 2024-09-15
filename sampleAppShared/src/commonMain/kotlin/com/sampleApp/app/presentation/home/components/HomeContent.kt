package com.sampleApp.app.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.buttons.SwitchButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.inputFields.OutlinedOtpInputField
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.metacto.core.presentation.theme.CoreTheme.colors
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State

@Composable
internal fun HomeContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    ScreenColumn(
        isScrollable = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        VideoPlayer(
            uniqueId = "home_video_player",
            videoUrl = state.currentVideo.url,
            videoTitle = state.currentVideo.title,
            videoArtist = state.currentVideo.artist,
            videoArtworkUrl = state.currentVideo.artworkUrl,
            autoPlay = false,
            scaleToCrop = true,
            enablePip = true,
            handleLifecyclePause = false,
            controllerShowTimeoutMs = 2000,
            onPlayerCreated = {
                onEvent(Event.VideoPlayerControllerCreated(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 1",
                onClick = {
                    onEvent(Event.ChangeCurrentVideo(0))
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 2",
                onClick = {
                    onEvent(Event.ChangeCurrentVideo(1))
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 3",
                onClick = {
                    onEvent(Event.ChangeCurrentVideo(2))
                }
            )
        }

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "To Youtube Screen",
            onClick = {
                onEvent(Event.NavToYoutubeScreen)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "To Test Screen",
            onClick = {
                onEvent(Event.NavToTestScreen)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Open Picker",
            onClick = {
                onEvent(Event.OpenPicker)
            }
        )

        SwitchButton(
            modifier = Modifier,
            isChecked = false,
            onCheckChanged = {

            }
        )

        SwitchButton(
            modifier = Modifier,
            isChecked = true,
            onCheckChanged = {

            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Share email",
            onClick = {
                onEvent(Event.ShareEmail)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Share text",
            onClick = {
                onEvent(Event.ShareText)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Open store",
            onClick = {
                onEvent(Event.OpenStore)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Open Phone",
            onClick = {
                onEvent(Event.OpenPhone)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Open Browser",
            onClick = {
                onEvent(Event.OpenBrowser)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Share image",
            onClick = {
                onEvent(Event.ShareImage)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Add to calendar",
            onClick = {
                onEvent(Event.AddToCalendar)
            }
        )

        OutlinedOtpInputField(
            backgroundColor = colors.white,
            modifier = Modifier.fillMaxWidth(),
            pinCount = 6,
            digitItemElevation = 6.dp
        )

        Text(
            "Picked Item: ${state.pickedItem?.title ?: "None"}"
        )
    }
}
