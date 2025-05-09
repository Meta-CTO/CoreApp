package com.metacto.core.presentation.components.videoPlayer

import android.app.PendingIntent
import androidx.mediarouter.app.MediaRouteButton
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.Icon
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
import androidx.lifecycle.repeatOnLifecycle
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
    private val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)

    private val pipActionsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val controlType = intent?.getIntExtra(EXTRA_CONTROL_TYPE, 0)
            val receivedPlayerId = intent?.getStringExtra(KEY_PLAYER_ID_PIP_ACTION)

            when (controlType) {
                CONTROL_TYPE_PLAY_PAUSE -> {
                    val currentPipActivePlayerId = eventBroadcaster.getPipActivePlayerId()

                    if (currentPipActivePlayerId != null && receivedPlayerId == currentPipActivePlayerId && isInPictureInPictureMode) {
                        playerManagers[currentPipActivePlayerId]?.let { pipManager ->
                            val pipExoPlayer = pipManager.exoPlayer
                            val wasPlaying = pipExoPlayer.isPlaying

                            if (wasPlaying) {
                                pipManager.pause()
                            } else {
                                pipManager.play()
                            }

                            // Add a small delay to ensure the player state has updated
                            lifecycleScope.launch {
                                kotlinx.coroutines.delay(50)
                                updatePipParams()

                                if (pipManager.getMediaMetadataEnabled()) {
                                    pipManager.notificationManager.showNotificationForPlayer(pipExoPlayer)
                                }
                            }
                        }
                    }
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
                    playerManagers[this@VideoPlayerActivity.playerId]?.addExternalSubtitle(language, fileName, content)
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

        val newPlayerIdFromIntent = intent?.getStringExtra(KEY_PLAYER_ID)
        if (newPlayerIdFromIntent != null) {
            this.playerId = newPlayerIdFromIntent
            configPlayerView(newPlayerIdFromIntent)
        } else {
            finish()
            return
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

    private fun enablePip() {
        if (!isPipSupported || this.playerId == null) return

        val currentPlayerManager = playerManagers[this.playerId!!]
        if (currentPlayerManager == null || !currentPlayerManager.isPipEnabled) return

        wasPlayingBeforePipEnter = currentPlayerManager.exoPlayer.isPlaying
        currentPlayerManager.saveState()

        updatePipParams()?.let {
            enterPictureInPictureMode(it)
        }
    }

    private fun createPipRemoteActions(): List<RemoteAction> {
        val pipPlayerId = this.playerId ?: return emptyList()
        val playerManager = playerManagers[pipPlayerId] ?: return emptyList()

        val isPlaying = playerManager.exoPlayer.isPlaying

        val playPauseIntent = Intent(ACTION_MEDIA_CONTROL).apply {
            putExtra(EXTRA_CONTROL_TYPE, CONTROL_TYPE_PLAY_PAUSE)
            putExtra(KEY_PLAYER_ID_PIP_ACTION, pipPlayerId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            CONTROL_TYPE_PLAY_PAUSE,
            playPauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val iconResId = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val title = ""

        return listOf(
            RemoteAction(
                Icon.createWithResource(this, iconResId),
                title,
                title,
                pendingIntent
            )
        )
    }

    private fun updatePipParams(): PictureInPictureParams? {
        val currentPlayerManager = playerManagers[this.playerId ?: return null]
        val currentExoPlayer = currentPlayerManager?.exoPlayer ?: return null

        val videoWidth = currentExoPlayer.videoSize.width
        val videoHeight = currentExoPlayer.videoSize.height

        val builder = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(videoWidth.coerceAtLeast(1), videoHeight.coerceAtLeast(1)))

        builder.setActions(createPipRemoteActions())

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

    @OptIn(UnstableApi::class)
    private fun configPlayerView(targetPlayerId: String) {
        val playerManager = playerManagers[targetPlayerId]
        if (playerManager == null) {
            finish()
            return
        }

        val newPlayerInstance = playerManager.exoPlayer

        if (this.exoPlayer != null && this.exoPlayer != newPlayerInstance) {
            this.exoPlayer?.removeListener(this)
        }

        this.exoPlayer = newPlayerInstance
        this.playerId = targetPlayerId

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
            if (isInPictureInPictureMode && eventBroadcaster.getPipActivePlayerId() == this.playerId) {
                updatePipParams()
            }
        }

        val castButton = findViewById<MediaRouteButton>(R.id.cast_button)
        playerManager.setupCastButton(castButton)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)

        if (isInPictureInPictureMode && eventBroadcaster.getPipActivePlayerId() == this.playerId) {
            updatePipParams()
        }
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)

        if (isInPictureInPictureMode && eventBroadcaster.getPipActivePlayerId() == this.playerId) {
            updatePipParams()
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        if (isInPictureInPictureMode) {
            updatePipParams()
        }
        val currentActivityPlayerId = this.playerId ?: return
        val currentManager = playerManagers[currentActivityPlayerId]

        val pipContainer = findViewById<FrameLayout>(R.id.pip_container)
        val topControlsContainer = findViewById<LinearLayout>(R.id.top_controls_container)

        if (isInPictureInPictureMode) {
            eventBroadcaster.setPipActivePlayerId(currentActivityPlayerId)

            playerView?.useController = false
            playerView?.hideController()
            pipContainer.visibility = View.GONE
            topControlsContainer.visibility = View.GONE
            playerView?.subtitleView?.visibility = View.INVISIBLE

            eventBroadcaster.emit(VideoPlayerEvent.StartedPip(currentActivityPlayerId))
        } else {
            val previouslyPipPlayerId = eventBroadcaster.getPipActivePlayerId()

            if (previouslyPipPlayerId == currentActivityPlayerId) {
                eventBroadcaster.setPipActivePlayerId(null)

                currentManager?.let {
                    if (!it.isExplicitlyPaused()) {
                        it.switchFromPip()
                    } else {
                        it.setExplicitlyPaused(false)
                    }
                }
            }

            playerView?.useController = true
            showSystemBars()
            pipContainer.visibility = if (isPipSupported) View.VISIBLE else View.GONE
            topControlsContainer.visibility = if (isPipSupported) View.VISIBLE else View.GONE
            playerView?.subtitleView?.visibility = View.VISIBLE

            configPlayerView(currentActivityPlayerId)

            if (wasPlayingBeforePipEnter && currentManager?.isExplicitlyPaused() == false && !currentManager.exoPlayer.isPlaying) {
                currentManager.exoPlayer.play()
            }
            wasPlayingBeforePipEnter = false

            eventBroadcaster.emit(VideoPlayerEvent.StoppedPip(currentActivityPlayerId))
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (this.playerId != null && playerManagers[this.playerId!!]?.isPipEnabled == true && playerManagers[this.playerId!!]?.exoPlayer?.isPlaying == true) {
            enablePip()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val oldPlayerId = this.playerId
        setIntent(intent)

        val newPlayerIdFromIntent = intent.getStringExtra(KEY_PLAYER_ID)
        if (newPlayerIdFromIntent != null) {
            if (newPlayerIdFromIntent != oldPlayerId) {
                this.playerId = newPlayerIdFromIntent
                configPlayerView(newPlayerIdFromIntent)
            } else {
                configPlayerView(newPlayerIdFromIntent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.playerId?.let {
            configPlayerView(it)
            val activePipId = eventBroadcaster.getPipActivePlayerId()
            if (it == activePipId && !isInPictureInPictureMode) {
                playerManagers[it]?.switchFromPip()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.playerId?.let {
            configPlayerView(it)
            val activePipId = eventBroadcaster.getPipActivePlayerId()
            if (it == activePipId && !isInPictureInPictureMode) {
                playerManagers[it]?.switchFromPip()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (!isInPictureInPictureMode && !isChangingConfigurations) {
            if (!isFinishing) {
                this.playerId?.let {
                    playerManagers[it]?.saveState()
                    if (playerManagers[it]?.exoPlayer?.isPlaying == true && playerManagers[it]?.isCasting?.value == false) {
                        playerManagers[it]?.exoPlayer?.pause()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pipActionsReceiver)
        this.exoPlayer?.removeListener(this)
        playerView?.player = null

        if (!isChangingConfigurations && this.playerId != null && eventBroadcaster.getPipActivePlayerId() == this.playerId) {
            eventBroadcaster.setPipActivePlayerId(null)
        }

        this.playerId?.let { eventBroadcaster.emit(VideoPlayerEvent.ActivityFinished(it)) }
        this.exoPlayer = null
        this.playerId = null
    }

    companion object {
        private const val KEY_PLAYER_ID = "player_id"
        private const val KEY_ENABLE_PIP = "enable_pip"
        const val ACTION_MEDIA_CONTROL = "com.metacto.core.presentation.components.videoPlayer.ACTION_MEDIA_CONTROL"
        const val EXTRA_CONTROL_TYPE = "control_type"
        const val CONTROL_TYPE_PLAY_PAUSE = 1
        const val KEY_PLAYER_ID_PIP_ACTION = "player_id_pip_action"

        fun start(context: Context, uniqueId: String, enablePip: Boolean) {
            val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                putExtra(KEY_PLAYER_ID, uniqueId)
                putExtra(KEY_ENABLE_PIP, enablePip)
            }
            context.startActivity(intent)
        }
    }
}