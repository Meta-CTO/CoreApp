package com.metacto.catalogapp.presentation.mediapicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.mediapicker.MediaPickerContract.Event
import com.metacto.catalogapp.presentation.mediapicker.components.MediaPickerContent
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.rememberViewModel

internal class MediaPickerScreen : CoreScreen<MediaPickerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MediaPickerViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        MediaPickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
