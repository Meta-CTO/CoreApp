package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVLayerVideoGravityResizeAspect
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVKit.AVPictureInPictureController
import platform.AVKit.AVPlayerViewController
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun VideoPlayer(
    modifier: Modifier,
    autoPlay: Boolean,
    scaleToCrop: Boolean,
    enablePip: Boolean,
    isPlaying: Boolean,
    url: String
) {
    val player = remember(url) {
        AVPlayer(uRL = NSURL.URLWithString(url)!!)
    }

    val playerLayer = remember(player) {
        AVPlayerLayer().apply {
            this.player = player
            videoGravity = if (scaleToCrop) {
                AVLayerVideoGravityResizeAspectFill
            } else {
                AVLayerVideoGravityResizeAspect
            }
        }
    }

    val playerController = remember(player) {
        AVPlayerViewController().apply {
            this.player = player
            showsPlaybackControls = true
            videoGravity = if (scaleToCrop) {
                AVLayerVideoGravityResizeAspectFill
            } else {
                AVLayerVideoGravityResizeAspect
            }
        }
    }

    var pipController: AVPictureInPictureController? by remember {
        mutableStateOf(null)
    }

    UIKitView(
        modifier = modifier,
        factory = {
            UIView().apply {
                addSubview(playerController.view)
            }

        },
        update = { view ->
            // Remove current subviews
            view.subviews().forEach { subView ->
                (subView as? UIView)?.removeFromSuperview()
            }

            // Then add the new player view
            view.addSubview(playerController.view)

            // Resize it
            playerLayer.setFrame(view.bounds)
            playerController.view.setFrame(view.bounds)
            playerController.view.layer.frame = view.bounds

            // Initialize PiP controller if required
            if (enablePip && pipController == null && AVPictureInPictureController.isPictureInPictureSupported()) {
                pipController = AVPictureInPictureController(playerLayer).apply {
                    canStartPictureInPictureAutomaticallyFromInline = true
                }
            }
            // Auto play if required
            if (autoPlay) {
                player.play()
                playerController.player?.play()
            }

            // Auto play if required
            if (isPlaying) {
                player.play()
                playerController.player?.play()
            } else {
                player.pause()
                playerController.player?.pause()
            }

            // Start PiP if it's not already running
            pipController.startIfPossible()
        },
        onResize = { view, rect ->
            CATransaction.run {
                begin()

                setValue(true, kCATransactionDisableActions)
                view.layer.setFrame(rect)
                playerLayer.setFrame(rect)
                playerController.view.layer.frame = rect

                commit()
            }
        },
        onRelease = {
            player.pause()
            playerController.player?.pause()
        }
    )

    // Pause player when disposed
    DisposableEffect(player) {
        onDispose {
            player.pause()
        }
    }
}

private fun AVPictureInPictureController?.startIfPossible() = this?.let {
    if (!isPictureInPictureActive()) {
        startPictureInPicture()
    }
}

private fun AVPictureInPictureController?.stopIfPossible() = this?.let {
    if (isPictureInPictureActive()) {
        stopPictureInPicture()
    }
}
