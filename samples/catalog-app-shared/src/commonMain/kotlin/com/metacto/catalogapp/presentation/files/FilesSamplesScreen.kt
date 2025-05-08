package com.metacto.catalogapp.presentation.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.Event
import com.metacto.catalogapp.presentation.files.components.FilesSamplesScreenContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class FilesSamplesScreen : BaseScreen<FilesSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<FilesSamplesViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        FilesSamplesScreenContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
