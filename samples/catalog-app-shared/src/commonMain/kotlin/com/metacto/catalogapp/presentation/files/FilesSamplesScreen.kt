package com.metacto.catalogapp.presentation.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.Event
import com.metacto.catalogapp.presentation.files.components.FilesSamplesContent
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.rememberViewModel

internal class FilesSamplesScreen : CoreScreen<FilesSamplesViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<FilesSamplesViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        FilesSamplesContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
