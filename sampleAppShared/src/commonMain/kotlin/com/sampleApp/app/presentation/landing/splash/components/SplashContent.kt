package com.sampleApp.app.presentation.landing.splash.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.inputFields.CurrencyAmountInputVisualTransformation
import com.metacto.core.presentation.components.inputFields.PriceTextInputField
import com.metacto.core.presentation.components.inputFields.PrimaryTextInputField
import com.metacto.core.presentation.components.videoPlayer.YoutubePlayer
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

ScreenColumn (){
    YoutubePlayer(modifier = Modifier.fillMaxSize(), url = "https://www.youtube.com/watch?v=oGT3Z7fVNc0")

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