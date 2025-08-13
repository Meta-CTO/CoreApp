package com.metacto.catalogapp.presentation.mediaManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerContract.Event
import com.metacto.catalogapp.presentation.mediaManager.components.MediaManagerContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class MediaManagerScreen : BaseScreen<MediaManagerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MediaManagerViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        MediaManagerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
