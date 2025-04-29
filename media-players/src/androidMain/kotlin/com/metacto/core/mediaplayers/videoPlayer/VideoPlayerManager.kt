package com.metacto.core.mediaplayers.videoPlayer

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import androidx.core.net.toUri
import com.metacto.core.mediaplayers.di.DiQualifiers
import com.metacto.core.mediaplayers.extensions.createMediaSource
import com.metacto.core.ui.extensions.getLauncherPendingIntent

@UnstableApi
internal class VideoPlayerManager(
    private val uniqueId: String
) : KoinComponent {

    private val context by inject<Context>()
    private val playerManagers by inject<MutableMap<String, VideoPlayerManager>>(DiQualifiers.videoPlayerManagers)
    private var isAutoPlay = false
    private var isMediaMetadataEnabled = false
    var onVideoLoop: (() -> Unit)? = null
    var onVideoEnd: (() -> Unit)? = null

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
        })
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

    fun setAutoRepeat(autoRepeat:Boolean){
        this.exoPlayer.repeatMode = if (autoRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
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

            // Set media source and prepare
            if (isAutoPlay) {
                playWhenReady = true
            }
            setMediaSource(mediaItem)
            prepare()
        }
    }

    fun play() {
        if (exoPlayer.isPlaying.not()) {
            exoPlayer.play()
        }
    }

    fun pause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        }
    }

    fun setMediaMetadataEnabled(isEnabled: Boolean) {
        isMediaMetadataEnabled = isEnabled
    }
}