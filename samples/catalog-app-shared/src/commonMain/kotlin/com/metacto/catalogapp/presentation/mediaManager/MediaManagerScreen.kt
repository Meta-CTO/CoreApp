package com.metacto.catalogapp.presentation.mediaManager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerContract.Event
import com.metacto.catalogapp.presentation.mediaManager.components.MediaManagerContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class MediaManagerScreen : BaseScreen<MediaManagerViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<MediaManagerViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        MediaManagerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
