package com.sampleApp.app.presentation.camera

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.presentation.camera.CameraController
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.camera.CameraContract.Effect
import com.sampleApp.app.presentation.camera.CameraContract.Event
import com.sampleApp.app.presentation.camera.CameraContract.State

class CameraViewModel(
    private val cameraController: CameraController
) : BaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.ToggleLens -> handleToggleLens()
        Event.ToggleRecord -> handleToggleRecord()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        handlePermissions()

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handlePermissions() = executeSilent({
        permissionManager.grantPermission(Permission.CAMERA)
        setState { copy(cameraController = this@CameraViewModel.cameraController) }
    })

    private fun handleToggleLens() {
        currentState.cameraController?.toggleCameraLens()
    }

    private fun handleToggleRecord() = executeCatching({
        if (currentState.isRecording) {
            val result = cameraController.stopRecording()
            setState { copy(isRecording = false) }
            println("Recording path: $result")
        } else {
            cameraController.recordVideo(
                params = VideoRecordingParams()
            )
            setState { copy(isRecording = true) }
        }
    })
}
