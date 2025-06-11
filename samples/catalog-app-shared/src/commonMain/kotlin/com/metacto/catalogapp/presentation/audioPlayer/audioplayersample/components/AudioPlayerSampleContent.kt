package com.metacto.catalogapp.presentation.audioPlayer.audioplayersample.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.audioPlayer.audioplayersample.AudioPlayerSampleContract.Event
import com.metacto.catalogapp.presentation.audioPlayer.audioplayersample.AudioPlayerSampleContract.State
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.inputFields.PrimaryTextInputField
import com.metacto.core.ui.mediaplayers.audioPlayer.AudioPlayer
import com.metacto.core.ui.mediaplayers.audioPlayer.AudioPlayerStatusListener
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun AudioPlayerSampleContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // audio test link
    // https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3

    // Di
    val navManager = koinInject<NavManager>()

    // states
    var audioUrl by remember { mutableStateOf("") }

    // Container column
    AppScreenColumn(
        title = "AudioPlayerSample",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        // Audio url input field
        PrimaryTextInputField(
            text = audioUrl,
            label = "Audio Url",
            onValueChange = {
                audioUrl = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Audio player
        AudioPlayer(
            audioUrl = audioUrl,
            title = "audio title",
            thumbnailUrl = "",
            audioPlayerStatusListener = object : AudioPlayerStatusListener {
                override fun onAudioPlayed() {
                }

                override fun onAudioPaused() {

                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

    }
}
