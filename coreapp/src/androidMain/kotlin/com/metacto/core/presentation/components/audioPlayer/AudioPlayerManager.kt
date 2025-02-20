package com.metacto.core.presentation.components.audioPlayer

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.metacto.core.domain.DiQualifiers
import com.metacto.core.utils.extensions.createMediaSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.milliseconds

internal class AudioPlayerManager(
    private val uniqueId: String,
    private val audioPlayerStatusListener: AudioPlayerStatusListener
) : KoinComponent {

    private val context by inject<Context>()
    private val playerManagers by inject<MutableMap<String, AudioPlayerManager>>(DiQualifiers.audioPlayerManagers)
    private var isAutoPlay = false

    private val _isPlaying = mutableStateOf(false)
    val isPlaying: State<Boolean> = _isPlaying

    private val _totalDuration = mutableLongStateOf(0)
    val totalDuration: State<Long> = _totalDuration

    // Define the exo player
    val exoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }

    init {
        // Add play listener to exo player
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_READY -> {
                        // Update total duration when the player is ready
                        _totalDuration.longValue = exoPlayer.duration
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                // Update isPlaying value
                _isPlaying.value = isPlaying

                // Skip if not playing
                if (isPlaying.not()) return

                // Pause other players
                playerManagers.values.forEach {
                    // Skip if it's current player
                    if (it.uniqueId == uniqueId) return@forEach

                    // Pause the player and hide the notification
                    it.exoPlayer.pause()
                }
            }
        })
    }

    fun setAutoPlay(isAutoPlay: Boolean) {
        this.isAutoPlay = isAutoPlay
    }

    @OptIn(UnstableApi::class)
    fun setMedia(audioUrl: String) {
        // If the player is already playing the same audio, skip
        if (exoPlayer.currentMediaItem?.mediaId == audioUrl) {
            return
        }

        exoPlayer.apply {
            // Create the media item
            val mediaItem = createMediaSource(url = audioUrl)

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
            audioPlayerStatusListener.onAudioPlayed()
        }
    }

    fun pause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
            audioPlayerStatusListener.onAudioPaused()
        }
    }

    fun togglePlay() {
        if (isPlaying.value) {
            pause()
        } else {
            // Reset if reached the end
            if (exoPlayer.currentPosition >= exoPlayer.duration) {
                exoPlayer.seekTo(0)
            }
            play()
        }
    }

    fun pollCurrentDuration(): Flow<Long> {
        return flow {
            while (exoPlayer.currentPosition <= exoPlayer.duration) {
                emit(exoPlayer.currentPosition)
                delay(DURATION_POLL_DELAY)
            }
        }.conflate()
    }

    companion object {
        private val DURATION_POLL_DELAY = 500.milliseconds
    }
}