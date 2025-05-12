package com.metacto.catalogapp.presentation.imagePreloader.imagepreloader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderContract.Event
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.components.ImagePreloaderContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class ImagePreloaderScreen : BaseScreen<ImagePreloaderViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<ImagePreloaderViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        ImagePreloaderContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
