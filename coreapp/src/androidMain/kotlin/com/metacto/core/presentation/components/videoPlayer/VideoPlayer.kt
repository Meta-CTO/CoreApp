package com.metacto.core.presentation.components.videoPlayer

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    autoPlay: Boolean,
    url: String
) {
    // TODO: implement using better library
    val context = LocalContext.current
    val mediaController = remember {
        MediaController(context)
    }

    AndroidView(
        modifier = modifier,
        factory = {
            VideoView(it).apply {
                setVideoPath(url)
                mediaController.setAnchorView(this)
                setMediaController(mediaController)
                start()
            }
        },
        update = {}
    )
}