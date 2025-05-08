package com.metacto.core.presentation.components.videoPlayer

import androidx.mediarouter.app.MediaRouteButton
import android.app.PictureInPictureParams
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.metacto.core.domain.DiQualifiers
import com.metacto.coreApp.R
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@OptIn(UnstableApi::class)
internal class VideoPlayerActivity : AppCompatActivity(), Player.Listener {
    private val eventBroadcaster: VideoPlayerEventBroadcaster by inject()
    private var exoPlayer: ExoPlayer? = null
    private var isPipSupported = false
    private var playerView: PlayerView? = null
    private var playerId: String? = null
    private var wasPlayingBeforePipEnter: Boolean = false

    // Receiver for PiP action controls
    private val pipActionsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra(EXTRA_CONTROL_TYPE, 0)) {
                CONTROL_TYPE_PLAY_PAUSE -> {
                    exoPlayer?.playWhenReady = !(exoPlayer?.playWhenReady ?: false)
                }
            }
        }
    }

    // File picker for subtitles
    private val subtitlePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data ?: return@registerForActivityResult
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            lifecycleScope.launch {
                val subtitleLoader = SubtitleFileLoader(this@VideoPlayerActivity)
                val fileLoaderResult = subtitleLoader.loadSubtitleFile(uri)

                if (fileLoaderResult != null) {
                    val (language, fileName, content) = fileLoaderResult
                    val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
                    playerManagers[playerId]?.addExternalSubtitle(language, fileName, content)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()

        setContentView(R.layout.activity_video_player)
        playerView = findViewById(R.id.player_view)

        isPipSupported =
            packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE) &&
                    intent?.getBooleanExtra(KEY_ENABLE_PIP, true) == true

        setupViews()

        // Get player ID from intent
        val newPlayerId = intent?.getStringExtra(KEY_PLAYER_ID)
        if (newPlayerId != null) {
            this.playerId = newPlayerId
            configPlayerView(newPlayerId)
        } else {
            finish()
            return
        }

        // Register receiver for PiP controls
        registerReceiver(pipActionsReceiver, IntentFilter(ACTION_MEDIA_CONTROL), RECEIVER_EXPORTED)
        handleBackPress()
        setupSubtitleButton()
    }

    private fun setupSubtitleButton() {
        val subtitleButton = findViewById<ImageButton>(R.id.ib_subtitle)
        subtitleButton.setOnClickListener {
            openSubtitleFilePicker()
        }
    }

    private fun openSubtitleFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                "text/vtt", "application/x-subrip", "application/ttml+xml", "text/x-ssa"
            ))
        }
        subtitlePickerLauncher.launch(intent)
    }

    private fun handleBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    // Hide system UI for fullscreen experience
    private fun hideSystemBars() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun showSystemBars() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }

    private fun setupViews() {
        val ibPip = findViewById<ImageButton>(R.id.ib_pip)
        ibPip.setOnClickListener {
            enablePip()
        }
        ibPip.visibility = if (isPipSupported) View.VISIBLE else View.GONE
    }

    // Enable Picture-in-Picture mode
    private fun enablePip() {
        if (!isPipSupported) return

        wasPlayingBeforePipEnter = exoPlayer?.isPlaying ?: false
        updatePipParams()?.let {
            enterPictureInPictureMode(it)
        }
    }

    // Update PiP parameters based on video dimensions
    private fun updatePipParams(): PictureInPictureParams? {

        val videoWidth = exoPlayer?.videoSize?.width ?: 16
        val videoHeight = exoPlayer?.videoSize?.height ?: 9

        val builder = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(videoWidth.coerceAtLeast(1), videoHeight.coerceAtLeast(1)))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setAutoEnterEnabled(true)
        }

        playerView?.let {
            val visibleRect = Rect()
            it.getGlobalVisibleRect(visibleRect)
            if (!visibleRect.isEmpty) {
                builder.setSourceRectHint(visibleRect)
            }
        }

        val params = builder.build()
        setPictureInPictureParams(params)
        return params
    }

    // Configure player view with the appropriate player manager
    @OptIn(UnstableApi::class)
    private fun configPlayerView(currentPlayerId: String) {
        val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
        val playerManager = playerManagers[currentPlayerId]
        val newPlayerInstanceFromManager = playerManager?.exoPlayer

        if (newPlayerInstanceFromManager == null) {
            finish()
            return
        }

        if (this.exoPlayer != null && this.exoPlayer != newPlayerInstanceFromManager) {
            this.exoPlayer?.removeListener(this)
        }

        this.exoPlayer = newPlayerInstanceFromManager

        playerView?.let {
            if (it.player != this.exoPlayer) {
                it.player = null
                it.player = this.exoPlayer
            } else {
                (it.videoSurfaceView as? SurfaceView)?.let { surfaceView ->
                    this.exoPlayer?.setVideoSurfaceView(surfaceView)
                }
            }
        }

        this.exoPlayer?.addListener(this)

        playerManager.setVideoSizeListener { _, _ ->
            if (isInPictureInPictureMode) {
                updatePipParams()
            }
        }

        val castButton = findViewById<MediaRouteButton>(R.id.cast_button)
        playerManager.setupCastButton(castButton)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        if (isInPictureInPictureMode) {
            updatePipParams()
        }
    }

    // Handle PiP mode changes
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        val pipContainer = findViewById<FrameLayout>(R.id.pip_container)
        val topControlsContainer = findViewById<LinearLayout>(R.id.top_controls_container)

        if (isInPictureInPictureMode) {
            playerId?.let { eventBroadcaster.setPipActivePlayerId(it) }
            playerView?.useController = false
            playerView?.hideController()
            pipContainer.visibility = View.GONE
            topControlsContainer.visibility = View.GONE
            playerView?.subtitleView?.visibility = View.INVISIBLE
            playerId?.let { eventBroadcaster.emit(VideoPlayerEvent.StartedPip(it)) }
        } else {
            if (playerId != null && eventBroadcaster.getPipActivePlayerId() == playerId) {
                eventBroadcaster.setPipActivePlayerId(null)
            }
            playerView?.useController = true
            showSystemBars()
            pipContainer.visibility = if (isPipSupported) View.VISIBLE else View.GONE
            topControlsContainer.visibility = if (isPipSupported) View.VISIBLE else View.GONE
            playerView?.subtitleView?.visibility = View.VISIBLE

            // When exiting PiP, ensure player is configured and playing if it was before
            playerId?.let {
                configPlayerView(it)
                if (wasPlayingBeforePipEnter && exoPlayer?.isPlaying == false) {
                    exoPlayer?.play()
                }
            }
            playerId?.let { eventBroadcaster.emit(VideoPlayerEvent.StoppedPip(it)) }
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
        setIntent(intent)
        intent.getStringExtra(KEY_PLAYER_ID)?.let {
            if (it != this.playerId) {
                this.playerId = it
                configPlayerView(it)
            } else {
                // If same player ID but activity brought to front
                configPlayerView(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Ensure player is configured when activity starts/restarts
        playerId?.let {
            configPlayerView(it)
            // If it was playing before PiP and we're not in PiP mode anymore, resume
            if (wasPlayingBeforePipEnter && !isInPictureInPictureMode && exoPlayer?.isPlaying == false) {
                exoPlayer?.play()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Similar to onStart, ensure player is configured and ready
        playerId?.let {
            configPlayerView(it)
            if (wasPlayingBeforePipEnter && !isInPictureInPictureMode && exoPlayer?.isPlaying == false) {
                exoPlayer?.play()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (!isInPictureInPictureMode && !isChangingConfigurations) {
            if (!isFinishing) {
                exoPlayer?.pause()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pipActionsReceiver)
        exoPlayer?.removeListener(this)
        playerView?.player = null
        if (playerId != null && eventBroadcaster.getPipActivePlayerId() == playerId) {
            eventBroadcaster.setPipActivePlayerId(null)
        }
        playerId?.let { eventBroadcaster.emit(VideoPlayerEvent.ActivityFinished(it)) }
    }

    companion object {
        private const val KEY_PLAYER_ID = "player_id"
        private const val KEY_ENABLE_PIP = "enable_pip"
        private const val ACTION_MEDIA_CONTROL = "media_control"
        private const val EXTRA_CONTROL_TYPE = "control_type"
        private const val CONTROL_TYPE_PLAY_PAUSE = 1

        fun start(
            context: Context,
            uniqueId: String,
            enablePip: Boolean = true
        ) {
            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                putExtra(KEY_PLAYER_ID, uniqueId)
                putExtra(KEY_ENABLE_PIP, enablePip)
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }
            context.startActivity(intent)
        }
    }
}