package com.sampleApp.app.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.core.navigation.NavManager
import com.metacto.core.presentation.components.audioPlayer.AudioPlayer
import com.metacto.core.presentation.components.audioPlayer.AudioPlayerStatusListener
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.images.AppImage
import com.metacto.core.presentation.components.inputFields.OutlinedOtpInputField
import com.metacto.core.presentation.components.inputFields.PickerInputField
import com.metacto.core.presentation.components.inputFields.PrimaryTextInputField
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.metacto.core.utils.contacts.rememberContactsCollectorOptionsFactory
import com.metacto.core.utils.language.English
import com.metacto.core.utils.language.ILanguageManager
import com.metacto.core.utils.language.Language
import com.metacto.core.utils.phoneNumber.IPhoneNumberManager
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.HomeContract.State
import com.sampleApp.app.presentation.models.VideoItemInfo
import com.sampleApp.app.presentation.test2.test2.Test2Screen
import com.sampleApp.app.presentation.theme.AppTheme
import com.sampleApp.app.resources.Res
import com.sampleApp.app.resources.toggle_language
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

private val LANGUAGES = mapOf(
    "en" to Language.English,
    "ar" to Language(
        code = "ar",
        name = "العربية",
        isRtl = true
    )
)

@Composable
internal fun HomeContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val contactsCollectorOptionsFactory = rememberContactsCollectorOptionsFactory()
    val languageManager = koinInject<ILanguageManager>()
    val navManager = koinInject<NavManager>()

    val videoInfo = remember {
        VideoItemInfo(
            url = "https://storage.sardius.media/-KrXWhrxRAYPfu44QPJ0/archives/DAA6A5576Dd5Ee41CBd6B68696F6/media/playlist.m3u8?feedId=27d824FCdF&vttUrl=https%253A%252F%252Fstorage.sardius.media%252F-KrXWhrxRAYPfu44QPJ0%252Farchives%252FDAA6A5576Dd5Ee41CBd6B68696F6%252Fstatic%252F1730996105358-1.vtt",
//            url = FileResources.intro_video.getUri(),
            title = "Random Video Title",
            artist = "Random Artist",
            artworkUrl = "https://yurielkaim.com/wp-content/uploads/2016/03/Happiness-Habits-10-Things-Happy-People-Do-Before-Bed-1200x900.jpg"
        )
    }

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
            uniqueId = "home_video_player",
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
        var otp by remember { mutableStateOf("") }
        OutlinedOtpInputField(
            pinCount = 6,
            text = otp,
            onValueChange = {
                otp = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        AppImage(
            url = "https://scstage103-cd.joycemeyer.org/-/media/JoyceMeyer/Ads/Books/Battlefield-of-the-Mind/BOTM_WebAd1.jpeg",
            extraHeaders = mapOf("Accept" to "image/png")
        )
        AudioPlayer(
            thumbnailUrl = "https://cdn.sanity.io/images/599r6htc/regionalized/a26fe0cf37bcc164980bcf8014817652df9683a7-1440x810.png",
            audioUrl = "https://commondatastorage.googleapis.com/codeskulptor-assets/Epoq-Lepidoptera.ogg",
            title = "testing testing testing testing testing testing testing testing testing",
            thumbnailSize = 50.dp,
            audioPlayerStatusListener = object :AudioPlayerStatusListener{
                override fun onAudioPlayed() {
                    println("Audio player played")
                }

                override fun onAudioPaused() {
                    println("Audio player paused")
                }
            }
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
            text = "To Test Screen 2",
            onClick = {
                navManager.navigate(Test2Screen())
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

                val options = contactsCollectorOptionsFactory.createOptions()
                // TODO: pass options to the view model
            }
        )

        Text(
            "Current language: ${languageManager.getCurrentLanguage().name}",
        )
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.toggle_language),
            onClick = {
                if (languageManager.getCurrentLanguage().code == "en") {
                    languageManager.changeLanguage(LANGUAGES["ar"]!!)
                } else {
                    languageManager.changeLanguage(LANGUAGES["en"]!!)
                }
            }
        )
    }
}
