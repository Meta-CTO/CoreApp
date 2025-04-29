package com.sampleApp.app.presentation.camera.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.metacto.core.mediaplayers.videoPlayer.ControlsType
import com.metacto.core.mediaplayers.videoPlayer.VideoPlayer
import com.metacto.core.navigation.NavManager
import com.metacto.core.presentation.camera.CameraPreview
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.sampleApp.app.presentation.camera.CameraContract.Event
import com.sampleApp.app.presentation.camera.CameraContract.State
import com.sampleApp.app.presentation.test.TestScreen
import com.sampleApp.app.presentation.theme.AppTheme.colors
import org.koin.compose.koinInject

@Composable
internal fun CameraContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    val navManager = koinInject<NavManager>()

    // Container column
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.black)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        if (state.cameraController != null) {
            CameraPreview(
                cameraController = state.cameraController,
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.black)
            )
        }

        if (state.recordingFilePath != null) {
            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                videoUrl = state.recordingFilePath,
                autoPlay = true,
                enablePip = false,
                controlsType = ControlsType.CustomControls,
                enableMediaMetadata = false,
                uniqueId = "camera-video-player"
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = if (state.isRecording) "Stop" else "Record",
                onClick = {
                    onEvent(Event.ToggleRecord)
                }
            )

            PrimaryFilledButton(
                modifier = Modifier.weight(1f),
                text = "Lens",
                onClick = {
                    onEvent(Event.ToggleLens)
                }
            )
        }
    }

    PrimaryFilledButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Nav to test screen",
        onClick = {
            navManager.navigate(TestScreen)
        }
    )

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PrimaryFilledButton(
            modifier = Modifier.weight(1f),
            text = "Go back",
            onClick = {
                onEvent(Event.BackClicked)
            }
        )

        PrimaryFilledButton(
            modifier = Modifier.weight(1f),
            text = "Retake",
            onClick = {
                onEvent(Event.RetakeClicked)
            }
        )
    }
}
