package com.metacto.core.ui.youtube.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.youtube.screen.YoutubeContract.Event
import com.metacto.core.ui.youtube.screen.components.YoutubeContent

class YoutubeScreen(
    private val videoId: String,
    private val shouldAutoPlay: Boolean = true,
    private val showControls: Boolean = true,
    private val showFullScreenButton: Boolean = true
) : CoreScreen<YoutubeViewModel>() {

    override val screenTag: String
        get() = "YoutubeScreen"

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<YoutubeViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                Event.Init(
                    videoId = videoId,
                    shouldAutoPlay = shouldAutoPlay,
                    showControls = showControls,
                    showFullScreenButton = showFullScreenButton
                )
            )
        }

        // Render content
        YoutubeContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}