package com.sampleApp.app.presentation.camera

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.camera.CameraController

class CameraContract {

    data class State(
        val isInitialized: Boolean = false,
        val cameraController: CameraController? = null,
        val isRecording: Boolean = false,
        val recordingFilePath: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(val cameraController: CameraController) : Event()
        data object ToggleRecord : Event()
        data object ToggleLens : Event()
    }

    sealed class Effect : ViewSideEffect
}
