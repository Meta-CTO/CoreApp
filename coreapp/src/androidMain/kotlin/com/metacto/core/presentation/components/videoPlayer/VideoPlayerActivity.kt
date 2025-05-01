package com.metacto.core.presentation.components.videoPlayer

import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.SurfaceView
import android.view.View
import android.widget.ImageButton
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.metacto.core.domain.DiQualifiers
import com.metacto.coreApp.R
import org.koin.android.ext.android.inject

@OptIn(UnstableApi::class)
internal class VideoPlayerActivity : AppCompatActivity() {
    private val eventBroadcaster: VideoPlayerEventBroadcaster by inject()
    private var exoPlayer: ExoPlayer? = null
    private var isPipSupported = false
    private var wasPlayingBeforePip = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view
        setContentView(R.layout.activity_video_player)

        // Check PiP support
        isPipSupported =
            packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE) &&
                    intent?.getBooleanExtra(KEY_ENABLE_PIP, true) == true

        // Setup views
        setupViews()

        // Config player view
        intent?.getStringExtra(KEY_PLAYER_ID)?.let {
            configPlayerView(it)
        }
    }

    private fun setupViews() {
        val ibPip = findViewById<ImageButton>(R.id.ib_pip)
        ibPip.setOnClickListener {
            enablePip()
        }
        ibPip.visibility = if (isPipSupported) View.VISIBLE else View.GONE
    }

    private fun enablePip() {
        if (!isPipSupported) return

        wasPlayingBeforePip = exoPlayer?.isPlaying ?: false

        val videoWidth = exoPlayer?.videoSize?.width ?: 16
        val videoHeight = exoPlayer?.videoSize?.height ?: 9

        val params = PictureInPictureParams.Builder().apply {
            setAspectRatio(Rational(videoWidth, videoHeight))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val playerView = findViewById<PlayerView>(R.id.player_view)
                playerView.videoSurfaceView?.let { surfaceView ->
                    val rect = Rect()
                    val coordinates = IntArray(2)
                    surfaceView.getLocationOnScreen(coordinates)
                    rect.set(
                        coordinates[0],
                        coordinates[1],
                        coordinates[0] + surfaceView.width,
                        coordinates[1] + surfaceView.height
                    )
                    setSourceRectHint(rect)
                }
            }
        }.build()

        enterPictureInPictureMode(params)
    }

    @OptIn(UnstableApi::class)
    private fun configPlayerView(playerId: String) {
        val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
        exoPlayer = playerManagers[playerId]?.exoPlayer ?: return

        val playerView = findViewById<PlayerView>(R.id.player_view)
        playerView.player = exoPlayer

        (playerView.videoSurfaceView as? SurfaceView)?.let {
            exoPlayer?.setVideoSurfaceView(it)
        }

        playerManagers[playerId]?.setVideoSizeListener { width, height ->
            if (isInPictureInPictureMode && width > 0 && height > 0) {
                val params = PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(width, height))
                    .build()
                setPictureInPictureParams(params)
            }
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        val playerView = findViewById<PlayerView>(R.id.player_view)

        if (isInPictureInPictureMode) {
            playerView.useController = false
            playerView.hideController()

            if (wasPlayingBeforePip) {
                exoPlayer?.play()
            }

            eventBroadcaster.emit(VideoPlayerEvent.StartedPip)
        } else {
            playerView.useController = true

            // Reattach the surface view to fix the freezing issue
            (playerView.videoSurfaceView as? SurfaceView)?.let {
                exoPlayer?.setVideoSurfaceView(it)
            }

            eventBroadcaster.emit(VideoPlayerEvent.StoppedPip)
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (isPipSupported && exoPlayer?.isPlaying == true) {
            enablePip()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.getStringExtra(KEY_PLAYER_ID)?.let {
            configPlayerView(it)
        }
    }

    override fun onStop() {
        if (!isInPictureInPictureMode) {
            eventBroadcaster.emit(VideoPlayerEvent.StoppedPip)
            exoPlayer?.pause()
        }
        super.onStop()
    }

    companion object {
        private const val KEY_PLAYER_ID = "player_id"
        private const val KEY_ENABLE_PIP = "enable_pip"

        fun start(context: Context, uniqueId: String, enablePip: Boolean = true) {
            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                putExtra(KEY_PLAYER_ID, uniqueId)
                putExtra(KEY_ENABLE_PIP, enablePip)
            }
            context.startActivity(intent)
        }
    }
}