package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRect
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

private const val TAG = "YoutubePlayer"

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun YoutubePlayer(
    modifier: Modifier,
    videoUrl: String?,
    videoId: String?,
    isPlaying: ((Boolean) -> Unit)?,
    isLoading: ((Boolean) -> Unit)?,
    onVideoEnded: (() -> Unit)?,
) {
    // Prepare and validate the url
    val theVideoUrl = videoUrl ?: videoId?.let { "https://www.youtube.com/watch?v=$it" }
    if (theVideoUrl == null) {
        println("$TAG - Error: Please pass valid videoUrl or valid videoId")
        return
    }

    val player = remember {
        NSURL.URLWithString(theVideoUrl)?.let { AVPlayer(uRL = it) }
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