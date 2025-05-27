package com.metacto.catalogapp.presentation.audioPlayer.audioplayersample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.audioPlayer.audioplayersample.AudioPlayerSampleContract.Event
import com.metacto.catalogapp.presentation.audioPlayer.audioplayersample.components.AudioPlayerSampleContent
import com.metacto.core.ui.base.BaseScreen
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel

internal class AudioPlayerSampleScreen : BaseScreen<AudioPlayerSampleViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<AudioPlayerSampleViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        AudioPlayerSampleContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
