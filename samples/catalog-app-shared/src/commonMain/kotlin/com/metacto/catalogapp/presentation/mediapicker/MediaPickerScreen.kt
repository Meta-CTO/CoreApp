package com.metacto.catalogapp.presentation.mediapicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.mediapicker.MediaPickerContract.Event
import com.metacto.catalogapp.presentation.mediapicker.components.MediaPickerContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class MediaPickerScreen : BaseScreen<MediaPickerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MediaPickerViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        MediaPickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
