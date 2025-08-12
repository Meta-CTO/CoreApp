package com.metacto.catalogapp.presentation.imagePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesContract.Event
import com.metacto.catalogapp.presentation.imagePicker.components.ImagePickerSamplesContent
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

 class ImagePickerSamplesScreen : CoreScreen<ImagePickerSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<ImagePickerSamplesViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        ImagePickerSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
