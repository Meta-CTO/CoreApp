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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.containers.ScreenColumn
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.SplashContract.State

@Composable
internal fun SplashContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val modifier = if (state.isWelcome) Modifier.background(Color.Blue) else Modifier
    var currentVideo by remember {
        mutableStateOf(videosList()[0])
    }

    ScreenColumn(
        isRefreshable = true,
        isRefreshing = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.Red)
        ) {

            VideoPlayer(
                url = currentVideo,
                autoPlay = true,
                scaleToCrop = true,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 1",
                onClick = {
                    currentVideo = videosList()[0]
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 2",
                onClick = {
                    currentVideo = videosList()[1]
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Video 3",
                onClick = {
                    currentVideo = videosList()[2]
                }
            )
        }

//        Text(
//            text = "Schedule repeating",
//            style = AppTheme.typography.fenwickBold24,
//            color = Color.Red,
//            modifier = modifier
//                .clickable { onEvent(Event.ScheduleRepeatingNotification) }
//        )
//
//        Text(
//            text = "Cancel scheduled",
//            style = AppTheme.typography.fenwickBold24,
//            color = Color.Red,
//            modifier = modifier
//                .clickable { onEvent(Event.CancelScheduledNotification) }
//        )
    }
}

private fun videosList() = listOf(
    "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
    "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
)