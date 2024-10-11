package com.sampleApp.app.presentation.camera

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.camera.CameraController
import com.metacto.core.presentation.components.videoPlayer.VideoPlayerController

class CameraContract {

    data class State(
        val isInitialized: Boolean = false,
        val cameraController: CameraController? = null,
        val videoController: VideoPlayerController? = null,
        val isRecording: Boolean = false,
        val recordingFilePath: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object BackClicked : Event()
        data object RetakeClicked : Event()
        data object ToggleRecord : Event()
        data object ToggleLens : Event()
        data class VideoControllerCreated(val controller: VideoPlayerController) : Event()
    }

    sealed class Effect : ViewSideEffect
}
