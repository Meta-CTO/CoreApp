package com.metacto.catalogapp.presentation.youtube.youtubesample.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import org.koin.compose.koinInject
import com.metacto.catalogapp.presentation.theme.colors
import com.metacto.catalogapp.presentation.theme.typography
import com.metacto.catalogapp.presentation.theme.shapes
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleContract.State
import com.metacto.catalogapp.presentation.youtube.youtubesample.YoutubeSampleContract.Event
import com.metacto.core.ui.components.buttons.PrimaryCheckableButton
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.buttons.SwitchButton
import com.metacto.core.ui.components.inputFields.PrimaryTextInputField
import com.metacto.core.ui.youtube.screen.YoutubeScreen

@Composable
internal fun YoutubeSampleContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // states
    var videoId by remember { mutableStateOf("") }
    var shouldAutoPlay by remember { mutableStateOf(true) }
    var showControls by remember { mutableStateOf(true) }
    var showFullScreenButton by remember { mutableStateOf(true) }

    // Container column
    AppScreenColumn(
        title = "Youtube Sample",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {

        // Video ID input field
        PrimaryTextInputField(
            text = videoId,
            label = "Video ID",
            onValueChange = {
                videoId = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Should auto play Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = spacings.spacing16)
        ) {
            Checkbox(
                checked = shouldAutoPlay,
                onCheckedChange = { shouldAutoPlay = it }
            )
            Text(text = "shouldAutoPlay")
        }

        // Show controls Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = spacings.spacing16)
        ) {
            Checkbox(
                checked = showControls,
                onCheckedChange = { showControls = it }
            )
            Text(text = "showControls")
        }

        // Show Full Screen Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = spacings.spacing16)
        ) {
            Checkbox(
                checked = showFullScreenButton,
                onCheckedChange = { showFullScreenButton = it }
            )
            Text(text = "showFullScreenButton")
        }

        // Open youtube video Button
        PrimaryFilledButton(
            text = "Open youtube video",
            onClick = {
                navManager.navigate(
                    YoutubeScreen(
                        videoId = videoId,
                        shouldAutoPlay = shouldAutoPlay,
                        showControls = showControls,
                        showFullScreenButton = showFullScreenButton
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )
    }
}
