package com.metacto.catalogapp.presentation.files.filessamplesscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.files.filessamplesscreen.FilesSamplesScreenContract.Event
import com.metacto.catalogapp.presentation.files.filessamplesscreen.components.FilesSamplesScreenContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class FilesSamplesScreenScreen : BaseScreen<FilesSamplesScreenViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<FilesSamplesScreenViewModel>()

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
