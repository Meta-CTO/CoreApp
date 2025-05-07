package com.metacto.core.presentation.components.videoPlayer

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
    private var wasPlayingBeforePip = false
    private var playerView: PlayerView? = null
    private var playerId: String? = null

    private val pipActionsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getIntExtra(EXTRA_CONTROL_TYPE, 0)) {
                CONTROL_TYPE_PLAY_PAUSE -> {
                    exoPlayer?.playWhenReady = !(exoPlayer?.playWhenReady ?: false)
                }
            }
        }
    }

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

        intent?.getStringExtra(KEY_PLAYER_ID)?.let {
            this.playerId = it
            configPlayerView(it)
        }
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
                "text/vtt",
                "application/x-subrip",
                "text/plain",
                "text/x-ssa"
            ))
        }
        subtitlePickerLauncher.launch(intent)
    }

    private fun handleBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isInPictureInPictureMode) {
                    finish()
                    return
                }
                this.remove()
                onBackPressedDispatcher.onBackPressed()
            }
        })
    }

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

        val castButton = findViewById<androidx.mediarouter.app.MediaRouteButton>(R.id.cast_button)
        val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
        playerId?.let { playerManagers[it]?.setupCastButton(castButton) }
    }

    private fun enablePip() {
        if (!isPipSupported) return
        wasPlayingBeforePip = exoPlayer?.isPlaying ?: false
        val params = updatePipParams()
        if (params != null) {
            enterPictureInPictureMode(params)
        }
    }

    private fun updatePipParams(): PictureInPictureParams? {
        val videoWidth = exoPlayer?.videoSize?.width ?: 16
        val videoHeight = exoPlayer?.videoSize?.height ?: 9

        val builder = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(videoWidth.coerceAtLeast(1), videoHeight.coerceAtLeast(1)))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setAutoEnterEnabled(true)
            builder.setSeamlessResizeEnabled(false)
        }

        playerView?.videoSurfaceView?.let { surfaceView ->
            val rect = Rect()
            surfaceView.getGlobalVisibleRect(rect)
            builder.setSourceRectHint(rect)
        }

        val params = builder.build()
        setPictureInPictureParams(params)
        return params
    }

    @OptIn(UnstableApi::class)
    private fun configPlayerView(playerId: String) {
        val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
        val playerManager = playerManagers[playerId]
        val newPlayer = playerManager?.exoPlayer

        if (newPlayer == null) {
            finish()
            return
        }

        exoPlayer?.removeListener(this)

        exoPlayer = newPlayer
        playerView?.player = exoPlayer
        exoPlayer?.addListener(this)

        (playerView?.videoSurfaceView as? SurfaceView)?.let {
            exoPlayer?.setVideoSurfaceView(it)
        }

        playerManager.setVideoSizeListener { width, height ->
            if (isInPictureInPictureMode) {
                updatePipParams()
            }
        }

        val castButton = findViewById<androidx.mediarouter.app.MediaRouteButton>(R.id.cast_button)
        playerManager.setupCastButton(castButton)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        if (isInPictureInPictureMode) {
            updatePipParams()
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        val pipContainer = findViewById<FrameLayout>(R.id.pip_container)
        val topControlsContainer = findViewById<LinearLayout>(R.id.top_controls_container)

        if (isInPictureInPictureMode) {
            eventBroadcaster.setPipActivePlayerId(playerId)

            val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
            playerManagers.forEach { (id, manager) ->
                if (id != playerId) {
                    manager.pause()
                }
            }

            playerView?.useController = false
            playerView?.hideController()
            pipContainer.visibility = View.GONE
            topControlsContainer.visibility = View.GONE
            playerView?.subtitleView?.visibility = View.INVISIBLE

            eventBroadcaster.emit(VideoPlayerEvent.StartedPip)
        } else {
            if (eventBroadcaster.getPipActivePlayerId() == playerId) {
                eventBroadcaster.setPipActivePlayerId(null)
            }

            playerView?.useController = true
            showSystemBars()
            pipContainer.visibility = if (isPipSupported) View.VISIBLE else View.GONE
            topControlsContainer.visibility = if (isPipSupported) View.VISIBLE else View.GONE
            playerView?.subtitleView?.visibility = View.VISIBLE

            if (playerView?.player == null) {
                playerView?.player = exoPlayer
            }
            (playerView?.videoSurfaceView as? SurfaceView)?.let {
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
            if (it != this.playerId) {
                this.playerId = it
                configPlayerView(it)
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
        exoPlayer = null

        if (eventBroadcaster.getPipActivePlayerId() == playerId) {
            eventBroadcaster.setPipActivePlayerId(null)
        }

        eventBroadcaster.emit(VideoPlayerEvent.ActivityFinished)
    }

    companion object {
        private const val KEY_PLAYER_ID = "player_id"
        private const val KEY_ENABLE_PIP = "enable_pip"
        private const val ACTION_MEDIA_CONTROL = "media_control"
        private const val EXTRA_CONTROL_TYPE = "control_type"
        private const val CONTROL_TYPE_PLAY_PAUSE = 1
        private const val RECEIVER_EXPORTED = Context.RECEIVER_EXPORTED

        fun start(
            context: Context,
            uniqueId: String,
            enablePip: Boolean = true
        ) {
            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                putExtra(KEY_PLAYER_ID, uniqueId)
                putExtra(KEY_ENABLE_PIP, enablePip)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            context.startActivity(intent)
        }
    }
}