package com.metacto.core.presentation.youtube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel
import com.metacto.core.presentation.youtube.YoutubeContract.Event
import com.metacto.core.presentation.youtube.components.YoutubeContent

class YoutubeScreen(
    private val videoId: String,
    private val shouldAutoPlay: Boolean = true,
    private val showControls: Boolean = true,
    private val showFullScreenButton: Boolean = true
) : BaseScreen<YoutubeViewModel>() {

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