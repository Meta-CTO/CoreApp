package com.sampleApp.app.presentation.camera

import com.metacto.core.domain.repos.UploadRepository
import com.metacto.core.presentation.camera.CameraController
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.models.VideoRecordingParams
import com.metacto.core.presentation.components.videoPlayer.VideoPlayerController
import com.metacto.core.utils.file.IFileManager
import com.metacto.core.utils.media.IMediaManager
import com.metacto.strapikmm.constants.SharedConstants
import com.metacto.strapikmm.sharedpreference.KmmPreference
import com.sampleApp.app.presentation.base.BaseViewModel
import com.sampleApp.app.presentation.camera.CameraContract.Effect
import com.sampleApp.app.presentation.camera.CameraContract.Event
import com.sampleApp.app.presentation.camera.CameraContract.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class CameraViewModel : BaseViewModel<State, Event, Effect>() {
    private val uploadRepository by inject<UploadRepository>()
    private val fileManager by inject<IFileManager>()
    private val sharedPreference by inject<KmmPreference>()
    private val cameraController by inject<CameraController>() {
        parametersOf(CameraLens.FRONT)
    }
    private val mediaManager by inject<IMediaManager>()

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.BackClicked -> navManager.goBack()
        Event.ToggleLens -> handleToggleLens()
        Event.ToggleRecord -> handleToggleRecord()
        is Event.VideoControllerCreated -> handleVideoControllerCreate(event.controller)
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setState { copy(cameraController = this@CameraViewModel.cameraController) }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleToggleLens() {
        currentState.cameraController?.toggleCameraLens()
    }

    private fun handleToggleRecord() = executeCatching(
        context = Dispatchers.IO,
        block = {
            val cameraController = currentState.cameraController ?: return@executeCatching
            if (currentState.isRecording) {
                val result = cameraController.stopRecording()
                setState {
                    copy(
                        isRecording = false,
                        recordingFilePath = result.videoPath
                    )
                }
                currentState.videoController?.play()

                // Upload it
                val videoBytes = fileManager.readFile(result.videoPath)
                sharedPreference.putSecureString(SharedConstants.ACCESS_TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNzI4NTA4MTI2LCJleHAiOjE3NjAwNDQxMjZ9.zXkg8CSK5PZLtrYgfcJzT5hAGju2CbwOSGGIEXcZCgU")
                val uploadResult = uploadRepository.uploadVideo(videoBytes)
                println("Upload video result: $uploadResult")

                val previewBytes = mediaManager.getVideoPreview(result.videoPath)

                if (previewBytes != null) {
                    val uploadPreviewResult = uploadRepository.uploadImage(previewBytes)
                    println("Upload preview result: $uploadPreviewResult")
                } else {
                    println("Failed to get video preview")
                }

            } else {
                cameraController.recordVideo(
                    params = VideoRecordingParams()
                )
                setState { copy(isRecording = true) }
            }
        }
    )

    private fun handleVideoControllerCreate(controller: VideoPlayerController) {
        setState { copy(videoController =  controller) }
    }
}
