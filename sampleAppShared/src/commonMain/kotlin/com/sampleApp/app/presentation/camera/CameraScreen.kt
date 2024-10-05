package com.sampleApp.app.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sampleApp.app.presentation.camera.CameraContract.Event
import com.sampleApp.app.presentation.camera.components.CameraContent
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel
import com.metacto.core.presentation.camera.models.CameraLens
import com.metacto.core.presentation.camera.rememberCameraController

internal object CameraScreen : BaseScreen<CameraViewModel>() {
    @Composable
    override fun Content() {
        // Get main objects
        val viewModel = rememberViewModel<CameraViewModel>()
        val cameraController = rememberCameraController(
            defaultLens = CameraLens.FRONT
        )

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                Event.Init(
                    cameraController = cameraController
                )
            )
        }

        // Render content
        CameraContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
