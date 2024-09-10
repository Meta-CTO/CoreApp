package com.metacto.core.presentation.components.youtubePlayer

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.metacto.strapikmm.util.Logger
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

private val logger = Logger("YoutubePlayer")

@Composable
actual fun YouTubePlayer(
    modifier: Modifier,
    videoId: String,
    onOrientationChanged: (isLandscape: Boolean) -> Unit
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            onOrientationChanged.invoke(true)
        }

        Configuration.ORIENTATION_PORTRAIT -> {
            onOrientationChanged.invoke(false)
        }

        else -> {
            onOrientationChanged.invoke(false)
        }
    }

    // Init things
    val context = LocalContext.current
    var player: YouTubePlayer? = null
    val activity = context as? Activity
    var theFullScreenView: View? = null

    // Create player fragment
    val playerFragment = remember {
        YouTubePlayerView(context)
    }

    // Attach full screen listener
    LaunchedEffect(playerFragment) {
        playerFragment.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                theFullScreenView = fullscreenView
                playerFragment.addView(fullscreenView)
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onExitFullscreen() {
                theFullScreenView?.let {
                    playerFragment.removeView(it)
                }
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        })
    }

    // Create player state listener
    val playerStateListener = remember {
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player = youTubePlayer
                youTubePlayer.loadVideo(videoId, 0f)
            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError,
            ) {
                super.onError(youTubePlayer, error)
                logger.log("iFramePlayer Error Reason = $error")
            }
        }
    }

    // Create player builder
    val playerBuilder = remember {
        IFramePlayerOptions.Builder().apply {
            autoplay(1)
            controls(1)
            rel(0)
            ivLoadPolicy(0)
            ccLoadPolicy(0)
            modestBranding(0)
            fullscreen(1)
        }
    }

    // Render player in android view
    AndroidView(
        modifier = modifier.background(Color.DarkGray),
        factory = {
            playerFragment.apply {
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
                enableAutomaticInitialization = false
                initialize(playerStateListener, playerBuilder.build())
            }
        }
    )

    // Handle component disposal
    DisposableEffect(Unit) {
        onDispose {
            playerFragment.removeYouTubePlayerListener(playerStateListener)
            playerFragment.release()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            player = null
        }
    }

    // Handle lifecycle
    OnLifecycleEvent(
        onResume = {
            player?.play()
        },
        onPause = {
            player?.pause()
        }
    )
}