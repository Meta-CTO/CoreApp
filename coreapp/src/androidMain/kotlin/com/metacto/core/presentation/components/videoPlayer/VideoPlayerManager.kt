package com.metacto.core.presentation.components.videoPlayer

import android.content.Context
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.metacto.core.domain.DiQualifiers
import com.metacto.core.utils.extensions.createMediaSource
import com.metacto.core.utils.extensions.getLauncherPendingIntent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@UnstableApi
internal class VideoPlayerManager(
    private val uniqueId: String
) : KoinComponent {

    private val context by inject<Context>()
    private val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    private var isAutoPlay = false
    private var isMediaMetadataEnabled = false
    private var castManager: CastManager? = null

    // Added for cast support
    private val _castAvailable = MutableStateFlow(false)
    val castAvailable: StateFlow<Boolean> = _castAvailable.asStateFlow()

    private val _isCasting = MutableStateFlow(false)
    val isCasting: StateFlow<Boolean> = _isCasting.asStateFlow()

    var onVideoLoop: (() -> Unit)? = null
    var onVideoEnd: (() -> Unit)? = null
    var isPipEnabled: Boolean = true
    private var videoSizeListener: ((width: Int, height: Int) -> Unit)? = null
    private var myMediaItem: MediaItem? = null

    // Define the exo player
    val exoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(10_000L)
            .setSeekForwardIncrementMs(10_000L)
            .build()
    }

    // Define the media session
    private val mediaSession by lazy {
        MediaSession.Builder(context, exoPlayer).run {
            this.setId(uniqueId)
            context.getLauncherPendingIntent()?.let {
                setSessionActivity(it)
            }
            build()
        }
    }

    // Define the notification manager
    private val notificationManager by lazy {
        MediaNotificationManager(
            context = context,
            sessionToken = mediaSession.token,
            player = exoPlayer
        )
    }

    init {
        // Add play listener to exo player
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                // Skip if the feature is not enabled
                if (isMediaMetadataEnabled.not()) return

                // Skip if not playing
                if (isPlaying.not()) return

                // Pause other players
                playerManagers.values.forEach {
                    // Skip if it's current player
                    if (it.uniqueId == uniqueId) return@forEach

                    // Pause the player and hide the notification
                    it.exoPlayer.pause()
                    it.notificationManager.hideNotification()
                }

                // Show the notification for current exo player
                notificationManager.showNotificationForPlayer(exoPlayer)
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                if (reason == Player.DISCONTINUITY_REASON_AUTO_TRANSITION) {
                    // Video has looped
                    onVideoLoop?.invoke()
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnd?.invoke()
                }
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                videoSizeListener?.invoke(videoSize.width, videoSize.height)
            }
        })

        // Initialize cast manager
        initCastManager()
    }

    private fun initCastManager() {
        try {
            castManager = CastManager(context).apply {
                // Set the local player for transfer between devices
                setLocalPlayer(exoPlayer)

                // Observe cast availability
                observeCastStates(this)
            }
        } catch (e: Exception) {
            // Cast not available or failed to initialize
            e.printStackTrace()
        }
    }

    private fun observeCastStates(castManager: CastManager) {
        // Collect cast state flows and update our own states
        try {
            // This is simplified - in a real app, you'd use lifecycleScope or similar
            _castAvailable.value = castManager.castAvailable.value
            _isCasting.value = castManager.isCasting.value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OptIn(UnstableApi::class)
    fun setScaleToCrop(isCrop: Boolean) {
        exoPlayer.videoScalingMode = when (isCrop) {
            true -> C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            false -> C.VIDEO_SCALING_MODE_DEFAULT
        }
    }

    fun setAutoPlay(isAutoPlay: Boolean) {
        this.isAutoPlay = isAutoPlay
    }

    fun setAutoRepeat(autoRepeat: Boolean) {
        this.exoPlayer.repeatMode =
            if (autoRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    fun setVideoSizeListener(listener: (width: Int, height: Int) -> Unit) {
        this.videoSizeListener = listener

        val currentWidth = exoPlayer.videoSize.width
        val currentHeight = exoPlayer.videoSize.height
        if (currentWidth > 0 && currentHeight > 0) {
            listener(currentWidth, currentHeight)
        }
    }

    @OptIn(UnstableApi::class)
    fun setMedia(
        videoUrl: String,
        videoTitle: String?,
        videoArtist: String?,
        videoArtworkUrl: String?
    ) {
        // If the player is already playing the same video, skip
        if (exoPlayer.currentMediaItem?.mediaId == videoUrl) {
            return
        }

        exoPlayer.apply {
            // Create the metadata
            val mediaMetaData = MediaMetadata.Builder()
                .setTitle(videoTitle.orEmpty())
                .setArtist(videoArtist.orEmpty())
                .setAlbumArtist(videoArtist.orEmpty())
                .setArtworkUri(videoArtworkUrl?.toUri())
                .build()

            // Create the media item
            val mediaItem = createMediaSource(
                context = context,
                url = videoUrl,
                metaData = mediaMetaData
            )

            // Store the media item for casting
            myMediaItem = MediaItem.Builder()
                .setUri(videoUrl.toUri())
                .setMediaId(videoUrl)
                .setMediaMetadata(mediaMetaData)
                .build()

            // Set media source and prepare
            if (isAutoPlay) {
                playWhenReady = true
            }
            setMediaSource(mediaItem)
            prepare()
        }
    }

    fun play() {
        if (_isCasting.value) {
            castManager?.getCurrentPlayer()?.play()
        } else if (exoPlayer.isPlaying.not()) {
            exoPlayer.play()
        }
    }

    fun pause() {
        if (_isCasting.value) {
            castManager?.getCurrentPlayer()?.pause()
        } else if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        }
    }

    fun startCasting() {
        myMediaItem?.let {
            castManager?.startCasting(it)
        }
    }

    fun stopCasting() {
        castManager?.stopCasting()
    }

    fun setupCastButton(mediaRouteButton: androidx.mediarouter.app.MediaRouteButton) {
        castManager?.setupCastButton(mediaRouteButton)
    }

    fun setMediaMetadataEnabled(isEnabled: Boolean) {
        isMediaMetadataEnabled = isEnabled
    }

    fun release() {
        castManager?.release()
        castManager = null
        exoPlayer.release()
    }
}