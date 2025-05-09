package com.metacto.catalogapp.presentation.imagePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.catalogapp.presentation.imagePicker.components.ImagePickerContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

 class ImagePickerScreen : BaseScreen<ImagePickerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<ImagePickerViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        ImagePickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
