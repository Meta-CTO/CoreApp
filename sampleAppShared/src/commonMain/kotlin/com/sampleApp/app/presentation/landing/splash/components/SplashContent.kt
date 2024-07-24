package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.youtubePlayer.SimpleYouTubePlayerOptionsBuilder
import com.metacto.core.presentation.components.youtubePlayer.YouTubePlayer
import com.metacto.core.presentation.components.youtubePlayer.YouTubePlayerHostState
import com.metacto.core.presentation.components.youtubePlayer.YouTubePlayerState
import com.metacto.core.presentation.components.youtubePlayer.YouTubeVideoId
import com.metacto.core.utils.CountDownTimer
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State
import kotlinx.coroutines.launch

@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val modifier = if (state.isWelcome) Modifier.background(Color.Blue) else Modifier
    var currentVideo by remember {
        mutableStateOf(videosList()[0])
    }

    val countDownTimer = remember {
        CountDownTimer(
            seconds = 10,
            onSecondsTick = {
                println("Timer ticked: $it")
            },
            onStopped = {
                println("Timer stopped")
            },
            onEnded = {
                println("Timer ended")
            }
        )
    }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
//        YoutubePlayer(
//            videoUrl = "https://www.youtube.com/watch?v=oGT3Z7fVNc0",
//            modifier = Modifier.fillMaxSize()
//        )

        val coroutineScope = rememberCoroutineScope()
        val hostState = remember { YouTubePlayerHostState() }

        when(val state = hostState.currentState) {
            is YouTubePlayerState.Error -> {
                //Text(text = "Error: ${state.message}")
            }
            YouTubePlayerState.Idle -> {
                // Do nothing, waiting for initialization
            }
            is YouTubePlayerState.Playing -> {
                // Update UI button states
            }
            YouTubePlayerState.Ready -> coroutineScope.launch {
                hostState.loadVideo(YouTubeVideoId("M7lc1UVf-VE"))
            }
        }

        YouTubePlayer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
//                .gesturesDisabled()
            ,
            hostState = hostState,
            options = SimpleYouTubePlayerOptionsBuilder.builder {
                autoplay(true)
                controls(true)
                rel(false)
                ivLoadPolicy(false)
                ccLoadPolicy(false)
                fullscreen(true)
                fullscreen = true
            },
        )

        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "FullScreen",
            onClick = {
                coroutineScope.launch {
                    hostState.toggleFullScreen()
                }
            }
        )
    }

//    ScreenColumn(
//        isRefreshable = true,
//        isRefreshing = false
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//                .background(Color.Red)
//        ) {
//
////            VideoPlayer(
////                url = currentVideo,
////                autoPlay = true,
////                scaleToCrop = false,
////                enablePip = true,
////                modifier = Modifier.fillMaxSize()
////            )
//
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            PrimaryFilledButton(
//                modifier = Modifier.weight(1f),
//                text = "Video 1",
//                onClick = {
//                    //currentVideo = videosList()[0]
//                    coroutineScope.launch {
//                        countDownTimer.start()
//                    }
//                }
//            )
//
//            PrimaryFilledButton(
//                modifier = Modifier.weight(1f),
//                text = "Video 2",
//                onClick = {
////                    currentVideo = videosList()[1]
//                    coroutineScope.launch {
//                        countDownTimer.stop()
//                    }
//                }
//            )
//
//            PrimaryFilledButton(
//                modifier = Modifier.weight(1f),
//                text = "Video 3",
//                onClick = {
////                    currentVideo = videosList()[2]
//                }
//            )
//        }
//
//        var price: Int? by remember {
//            mutableStateOf(null)
//        }
//        PriceTextInputField(
//            modifier = Modifier.fillMaxWidth(),
//            price = price,
//            onPriceChange = { price = it }
//        )
//
//        var fieldText by remember {
//            mutableStateOf("")
//        }
//        val visualTransformation = remember {
//            CurrencyAmountInputVisualTransformation()
//        }
//        PrimaryTextInputField(
//            modifier = Modifier.fillMaxWidth(),
//            text = fieldText,
//            onValueChange = {
//                fieldText = it
//            },
//            visualTransformation = visualTransformation,
//            allowDigitsOnly = true,
//            keyboardType = KeyboardType.Number
//        )
//
//
////        Text(
////            text = "Schedule repeating",
////            style = AppTheme.typography.fenwickBold24,
////            color = Color.Red,
////            modifier = modifier
////                .clickable { onEvent(Event.ScheduleRepeatingNotification) }
////        )
////
////        Text(
////            text = "Cancel scheduled",
////            style = AppTheme.typography.fenwickBold24,
////            color = Color.Red,
////            modifier = modifier
////                .clickable { onEvent(Event.CancelScheduledNotification) }
////        )
//    }
}

private fun videosList() = listOf(
    "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
    "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8",
    "https://storage.sardius.media/1088c54907d9370/archives/f15706391E53Fb46B0B52E92786B/media/playlist.m3u8?feedId=CA8a1f507a"
)