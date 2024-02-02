package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVLayerVideoGravityResizeAspect
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.play
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

    UIKitView(
        modifier = modifier,
        factory = {
            UIView().apply {
                addSubview(playerController.view)
            }
        },
        update = {
            if (autoPlay) {
                player.play()
                playerController.player?.play()
            }
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
        }
    )
}