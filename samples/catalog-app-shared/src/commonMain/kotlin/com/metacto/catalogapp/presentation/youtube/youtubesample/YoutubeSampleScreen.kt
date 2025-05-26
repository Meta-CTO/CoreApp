package com.metacto.catalogapp.presentation.youtube.youtubesample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleContract.Event
import com.metacto.catalogapp.presentation.youtube.youtubesample.components.YoutubeSampleContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class YoutubeSampleScreen : BaseScreen<YoutubeSampleViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<YoutubeSampleViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        YoutubeSampleContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
