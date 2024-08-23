package com.metacto.core.presentation.components.videoPlayer

import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.metacto.core.utils.extensions.createMediaSource
import org.koin.compose.rememberKoinInject

@OptIn(UnstableApi::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    videoUrl: String,
    videoArtist: String?,
    videoTitle: String?,
    videoArtworkUrl: String?,
    autoPlay: Boolean,
    scaleToCrop: Boolean,
    enablePip: Boolean,
    handleLifecyclePause: Boolean,
    controllerShowTimeoutMs: Int,
    onPlayerCreated: ((VideoPlayerController) -> Unit)?
) {
    // Inject main stuff
    val context = LocalContext.current
    val exoPlayer = rememberKoinInject<ExoPlayer>()
    val mediaNotificationManager = rememberKoinInject<MediaNotificationManager>()

    // Setup scaling mode
    LaunchedEffect(scaleToCrop) {
        exoPlayer.videoScalingMode = when (scaleToCrop) {
            true -> C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            false -> C.VIDEO_SCALING_MODE_DEFAULT
        }
    }

    // Setup auto play
    LaunchedEffect(autoPlay) {
        exoPlayer.playWhenReady = autoPlay
    }

    // Configure the player
    LaunchedEffect(videoUrl, videoTitle, videoArtist, videoArtworkUrl) {
        if (exoPlayer.isPlaying && exoPlayer.currentMediaItem?.mediaId == videoUrl) {
            return@LaunchedEffect
        }

        exoPlayer.apply {
            // Create the metadata
            val mediaMetaData = MediaMetadata.Builder()
                .setTitle(videoTitle.orEmpty())
                .setArtist(videoArtist.orEmpty())
                .setAlbumArtist(videoArtist.orEmpty())
                .setArtworkUri(videoArtworkUrl?.let { Uri.parse(it) })
                .build()

            // Create the media item
            val mediaItem = createMediaSource(
                url = videoUrl,
                metaData = mediaMetaData
            )

            // Set media source and prepare
            setMediaSource(mediaItem)
            prepare()
        }

        // Show player notification
        mediaNotificationManager.showNotificationForPlayer(exoPlayer)
    }

    // Create the player controller
    val playerController = remember(exoPlayer) {
        object : VideoPlayerController {
            override fun play() {
                exoPlayer.play()
            }

            override fun pause() {
                exoPlayer.pause()
            }
        }
    }

    // Launched effect to invoke player created
    LaunchedEffect(playerController, onPlayerCreated) {
        onPlayerCreated?.invoke(playerController)
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                useController = true
                this.controllerShowTimeoutMs = controllerShowTimeoutMs
                resizeMode = when (scaleToCrop) {
                    true -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                    false -> AspectRatioFrameLayout.RESIZE_MODE_FIT
                }

                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            if (handleLifecyclePause) {
                exoPlayer.pause()
                mediaNotificationManager.hideNotification()
            }
        }
    }

    // Handle lifecycle
    OnLifecycleEvent(
        onPause = {
            if (handleLifecyclePause) {
                exoPlayer.pause()
            }
        }
    )
}