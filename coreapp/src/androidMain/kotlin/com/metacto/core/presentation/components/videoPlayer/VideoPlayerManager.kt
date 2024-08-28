package com.metacto.core.presentation.components.videoPlayer

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.metacto.core.utils.extensions.createMediaSource
import com.metacto.core.utils.extensions.getLauncherPendingIntent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class VideoPlayerManager(
    private val uniqueId: String
) : KoinComponent {

    private val context by inject<Context>()
    private val playerManagers by inject<MutableMap<String, VideoPlayerManager>>()

    // Define the exo player
    val exoPlayer by lazy {
        ExoPlayer.Builder(context).build()
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
        exoPlayer.playWhenReady = isAutoPlay
    }

    @OptIn(UnstableApi::class)
    fun setMedia(
        videoUrl: String,
        videoTitle: String?,
        videoArtist: String?,
        videoArtworkUrl: String?
    ) {
        // If the player is already playing the same video, skip
        if (exoPlayer.isPlaying && exoPlayer.currentMediaItem?.mediaId == videoUrl) {
            return
        }

        exoPlayer.apply {
            // Create the metadata
            val mediaMetaData = MediaMetadata.Builder()
                .setTitle(videoTitle.orEmpty())
                .setArtist(videoArtist.orEmpty())
                .setAlbumArtist(videoArtist.orEmpty())
                .setArtworkUri(videoArtworkUrl?.let { Uri.parse(it) })
                .build()

            // Create the media item
            val mediaItem = createMediaSource(
                url = videoUrl,
                metaData = mediaMetaData
            )

            // Set media source and prepare
            setMediaSource(mediaItem)
            prepare()
        }
    }

    fun play() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }
}