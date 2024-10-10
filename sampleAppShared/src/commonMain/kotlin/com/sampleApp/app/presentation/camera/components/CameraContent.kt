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
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.camera.CameraPreview
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.metacto.core.presentation.components.videoPlayer.VideoPlayer
import com.metacto.core.utils.extensions.randomUUID
import com.sampleApp.app.presentation.camera.CameraContract.Event
import com.sampleApp.app.presentation.camera.CameraContract.State
import com.sampleApp.app.presentation.theme.AppTheme.colors

@Composable
internal fun CameraContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container column
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.black)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        if (state.recordingFilePath != null) {
            VideoPlayer(
                modifier = Modifier.fillMaxSize(),
                videoUrl = state.recordingFilePath,
                autoPlay = true,
                enablePip = false,
                showControls = false,
                uniqueId = randomUUID()
            )
        } else {
            if (state.cameraController != null) {
                CameraPreview(
                    cameraController = state.cameraController,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.black)
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
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            text = "Go back",
            onClick = {
                onEvent(Event.BackClicked)
            }
        )
    }
}
