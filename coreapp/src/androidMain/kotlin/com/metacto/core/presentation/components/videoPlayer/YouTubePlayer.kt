package com.metacto.core.presentation.components.videoPlayer

import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.metacto.core.utils.extensions.OnLifecycleEvent
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

private const val TAG = "YoutubePlayer"

@RequiresApi(Build.VERSION_CODES.S)
@Composable
actual fun YoutubePlayer(
    modifier: Modifier,
    videoUrl: String?,
    videoId: String?,
    isPlaying: ((Boolean) -> Unit)?,
    isLoading: ((Boolean) -> Unit)?,
    onVideoEnded: (() -> Unit)?,
) {
    // Prepare and validate the id
    val theVideoId = videoId ?: extractVideoId(videoUrl)
    if (theVideoId == null) {
        println("$TAG - Error: Please pass valid videoUrl or valid videoId")
        return
    }

    // Init things
    val context = LocalContext.current
    var player: YouTubePlayer? = null
    val activity = context as ComponentActivity

    // Create player fragment
    val playerFragment = remember {
        YouTubePlayerView(context)
    }

    // Attach full screen listener
    LaunchedEffect(playerFragment) {
        playerFragment.addFullscreenListener(object : FullscreenListener {

            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                playerFragment.addView(fullscreenView)
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            override fun onExitFullscreen() {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        })
    }

    // Create player state listener
    val playerStateListener = remember {
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                player = youTubePlayer
                youTubePlayer.loadVideo(theVideoId, 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState,
            ) {
                super.onStateChange(youTubePlayer, state)
                when (state) {
                    PlayerConstants.PlayerState.BUFFERING -> {
                        isLoading?.invoke(true)
                        isPlaying?.invoke(false)
                    }

                    PlayerConstants.PlayerState.PLAYING -> {
                        isLoading?.invoke(false)
                        isPlaying?.invoke(true)
                    }

                    PlayerConstants.PlayerState.ENDED -> {
                        isPlaying?.invoke(false)
                        isLoading?.invoke(false)
                        onVideoEnded?.invoke()
                    }

                    else -> {}
                }
            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError,
            ) {
                super.onError(youTubePlayer, error)
                println("$TAG - iFramePlayer Error Reason = $error")
            }
        }
    }

    // Create player builder
    val playerBuilder = remember {
        IFramePlayerOptions.Builder().apply {
            controls(1)
            fullscreen(1)
            autoplay(1)
            modestBranding(0)
            ccLoadPolicy(1)
            rel(0)
        }
    }

    // Render player in android view
    AndroidView(
        modifier = modifier.background(Color.DarkGray),
        factory = {
            playerFragment.apply {
                matchParent()
                enableAutomaticInitialization = false
                initialize(playerStateListener, playerBuilder.build())
            }
        }
    )

    // Handle component disposal
    DisposableEffect(theVideoId) {
        onDispose {
            onDispose {
                playerFragment.removeYouTubePlayerListener(playerStateListener)
                playerFragment.release()
                player = null
            }
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

private fun extractVideoId(url: String?): String? {
    return url?.let {
        val regex = Regex(
            """(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/\s]{11})"""
        )
        regex.find(it)?.groupValues?.getOrNull(1)
    }
}