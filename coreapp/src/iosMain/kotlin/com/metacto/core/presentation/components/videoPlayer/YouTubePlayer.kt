package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRect
import platform.Foundation.*
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.*
import platform.UIKit.UIView
import platform.darwin.*

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun YoutubePlayer(
    modifier: Modifier,
    url: String,
) {
    val player = remember {
        when {
            url?.contains("youtube.com") == true || url?.contains("youtu.be") == true -> {
                NSURL.URLWithString(url.toString())?.let { AVPlayer(uRL = it) }
            }

            isVideoFile(url) -> {
                NSURL.URLWithString(url.toString())?.let { AVPlayer(uRL = it) }
            }

            else -> null
        }
    }
    val playerLayer = remember { AVPlayerLayer() }
    val avPlayerViewController = remember { AVPlayerViewController() }
    avPlayerViewController.player = player
    avPlayerViewController.showsPlaybackControls = true

    playerLayer.player = player
    UIKitView(
        factory = {
            val playerContainer = UIView()
            playerContainer.addSubview(avPlayerViewController.view)
            playerContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            playerLayer.setFrame(rect)
            avPlayerViewController.view.layer.frame = rect
            CATransaction.commit()
        },
        update = { view ->
            player?.play()
            avPlayerViewController.player?.play()
        },
        modifier = modifier
    )
}

fun isVideoFile(url: String?): Boolean {
    return url?.matches(Regex(".*\\.(mp4|mkv|webm|avi|mov|wmv|flv|m4v|3gp|mpeg)\$", RegexOption.IGNORE_CASE)) == true
}