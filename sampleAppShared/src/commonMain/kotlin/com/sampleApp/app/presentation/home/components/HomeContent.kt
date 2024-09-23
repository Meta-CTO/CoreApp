package com.sampleApp.app.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.buttons.SwitchButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.inputFields.PriceTextInputField
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
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
            text = "To Camera Screen",
            onClick = {
                onEvent(Event.NavigateToCameraScreen)
            }
        )

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

        var price by remember { mutableStateOf("1.222222222222222222") }

        PriceTextInputField(
            modifier = Modifier.fillMaxWidth(),
            allowDecimal = true,
            maxAllowedDecimals = 3,
            price = price,
            onPriceChange = {value ->
                price = value.orEmpty()
            }
        )

        Text(
            "Picked Item: ${state.pickedItem?.title ?: "None"}"
        )
    }
}
