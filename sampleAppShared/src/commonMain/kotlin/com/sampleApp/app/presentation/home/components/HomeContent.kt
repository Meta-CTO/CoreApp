package com.sampleApp.app.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.inputFields.PickerInputField
import com.metacto.core.presentation.components.inputFields.PrimaryTextInputField
import com.metacto.core.presentation.components.videoPlayer.ControlsType
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.theme.AppTheme
import org.koin.compose.koinInject

@Composable
internal fun HomeContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    ScreenColumn(
        isScrollable = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        enableSafeInsets = true,
        onScroll = {
            println("HomeContent -- onScroll")
        },
        onScrollUp = {
            println("HomeContent -- onScrollUp")
        },
        onScrollDown = {
            println("HomeContent -- onScrollDown")
        },
    ) {
        VideoPlayer(
            uniqueId = "home_video_player",
            videoUrl = state.currentVideo.url,
            videoTitle = state.currentVideo.title,
            videoArtist = state.currentVideo.artist,
            videoArtworkUrl = state.currentVideo.artworkUrl,
            enableVoice = false,
            autoPlay = false,
            scaleToCrop = true,
            autoRepeat = true,
            enablePip = true,
            controlsType = ControlsType.CustomControls,
            handleLifecyclePause = false,
            controllerShowTimeoutMs = 2000,
            onPlayerCreated = {
                onEvent(Event.VideoPlayerControllerCreated(it))
            },
            onDurationCaught = {
                println("Video duration: $it")
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

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Request camera permissions",
            onClick = {
                onEvent(Event.RequestCameraPermClicked)
            }
        )

        var text by remember { mutableStateOf("") }
        PrimaryTextInputField(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            maxLines = 20,
            singleLine = false,
            placeholderMaxLines = 20,
            minHeight = AppTheme.spacings.spacing100,
            placeholder = "Some long placeholder text that should wrap to the next line if it's too long to fit in one line",
            onValueChange = {
                text = it
            }
        )

        PickerInputField(
            startIconVector = Icons.Default.Work,
            text = "",
            onClick = {}
        )

        var phoneNumber by remember { mutableStateOf("") }
//        val phoneNumberUtil = rememberPhoneNumberUtil()
//        val phoneNumberVisualTransformation = remember {
//            PhoneNumberVisualTransformation(phoneNumberUtil)
//        }
        val phoneNumberManager = koinInject<IPhoneNumberManager>()
        PrimaryTextInputField(
            label = "Phone number",
//            visualTransformation = phoneNumberVisualTransformation,
            modifier = Modifier.fillMaxWidth(),
            text = phoneNumber,
            onValueChange = {
                phoneNumber = it
            }
        )
        var countryCode by remember { mutableStateOf("US") }
        PrimaryTextInputField(
            label = "Country Code",
            modifier = Modifier.fillMaxWidth(),
            text = countryCode,
            onValueChange = {
                countryCode = it
            }
        )
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Do Phone number magic!",
            onClick = {
                val validPhoneNumber = phoneNumberManager.getValidPhoneNumber(phoneNumber, countryCode)
                println("PhoneNumberManager -- validPhoneNumber: $validPhoneNumber")
                val isValid = phoneNumberManager.isValidPhoneNumber(phoneNumber, countryCode)
                println("PhoneNumberManager --isValid: $isValid")
                val formattedPhoneNumber = phoneNumberManager.getFormattedPhoneNumber(phoneNumber, countryCode)
                println("PhoneNumberManager --formattedPhoneNumber: $formattedPhoneNumber")
                val getE164FormattedPhoneNumber = phoneNumberManager.getE164FormattedPhoneNumber(phoneNumber, countryCode)
                println("PhoneNumberManager --getE164FormattedPhoneNumber: $getE164FormattedPhoneNumber")
            }
        )
    }
}
