package com.metacto.catalogapp.presentation.videoPlayer.videoplayersample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.videoPlayer.videoplayersample.VideoPlayerSampleContract.Event
import com.metacto.catalogapp.presentation.videoPlayer.videoplayersample.components.VideoPlayerSampleContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class VideoPlayerSampleScreen : BaseScreen<VideoPlayerSampleViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<VideoPlayerSampleViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        VideoPlayerSampleContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
