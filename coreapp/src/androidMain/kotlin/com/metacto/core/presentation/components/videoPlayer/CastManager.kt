package com.metacto.core.presentation.components.videoPlayer

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.cast.CastPlayer
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.Executors

@OptIn(UnstableApi::class)
class CastManager(private val context: Context) {

    private var castPlayer: CastPlayer? = null
    private var castContext: CastContext? = null
    private var currentPlayer: Player? = null
    private var localExoPlayer: ExoPlayer? = null

    private var currentMediaItem: MediaItem? = null
    private var playbackPosition: Long = 0
    private var wasPlaying: Boolean = false

    private val _castAvailable = MutableStateFlow(false)
    val castAvailable: StateFlow<Boolean> = _castAvailable.asStateFlow()

    private val _isCasting = MutableStateFlow(false)
    val isCasting: StateFlow<Boolean> = _isCasting.asStateFlow()


    private val sessionManagerListener = object : SessionManagerListener<CastSession> {
        override fun onSessionStarting(session: CastSession) {
            // Session is starting
        }

        override fun onSessionStarted(session: CastSession, sessionId: String) {
            onCastSessionStarted()
        }

        override fun onSessionStartFailed(session: CastSession, error: Int) {
            _isCasting.value = false
        }

        override fun onSessionEnding(session: CastSession) {
            // Session is ending
        }

        override fun onSessionEnded(session: CastSession, error: Int) {
            onCastSessionEnded()
        }

        override fun onSessionResuming(session: CastSession, sessionId: String) {
            // Session is resuming
        }

        override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
            onCastSessionStarted()
        }

        override fun onSessionResumeFailed(session: CastSession, error: Int) {
            _isCasting.value = false
        }

        override fun onSessionSuspended(session: CastSession, reason: Int) {
            _isCasting.value = false
        }
    }

    init {
        if (isGooglePlayServicesAvailable()) {
            initializeCast()
        } else {
            _castAvailable.value = false
        }
    }

    private fun initializeCast() {
        val executor = Executors.newSingleThreadExecutor()
        CastContext.getSharedInstance(context, executor)
            .addOnSuccessListener { ctx ->
                castContext = ctx

                // Add listener for cast state changes
                ctx.addCastStateListener { state ->
                    _castAvailable.value = state != CastState.NO_DEVICES_AVAILABLE
                }

                // Create cast player
                castPlayer = CastPlayer(ctx).apply {
                    setSessionAvailabilityListener(object : SessionAvailabilityListener {
                        override fun onCastSessionAvailable() {
                            _isCasting.value = true
                        }

                        override fun onCastSessionUnavailable() {
                            _isCasting.value = false
                        }
                    })
                }

                // Add cast session manager listener
                ctx.sessionManager.addSessionManagerListener(
                    sessionManagerListener,
                    CastSession::class.java
                )

                // Check initial cast state
                _castAvailable.value = ctx.castState != CastState.NO_DEVICES_AVAILABLE

                // Check if already casting
                val castSession = ctx.sessionManager.currentCastSession
                if (castSession != null && castSession.isConnected) {
                    onCastSessionStarted()
                }
            }
            .addOnFailureListener {
                _castAvailable.value = false
                _isCasting.value = false
            }
    }

    fun setupCastButton(mediaRouteButton: androidx.mediarouter.app.MediaRouteButton) {
        castContext?.let {
            CastButtonFactory.setUpMediaRouteButton(context, mediaRouteButton)
        }
    }

    fun setLocalPlayer(exoPlayer: ExoPlayer) {
        localExoPlayer = exoPlayer
        // If we're not casting, set the current player to the local player
        if (!_isCasting.value) {
            currentPlayer = exoPlayer
        }
    }

    private fun onCastSessionStarted() {
        // Save the local player state
        localExoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            wasPlaying = exoPlayer.isPlaying
            currentMediaItem = currentMediaItem ?: exoPlayer.currentMediaItem

            // Pause local playback
            exoPlayer.pause()
        }

        // Set current player to cast player
        currentPlayer = castPlayer
        _isCasting.value = true

        // Transfer playback if media item is available
        currentMediaItem?.let { mediaItem ->
            castPlayer?.setMediaItem(mediaItem, playbackPosition)
            castPlayer?.prepare()
            if (wasPlaying) {
                castPlayer?.play()
            }
        }
    }

    private fun onCastSessionEnded() {
        // Set current player back to local player
        currentPlayer = localExoPlayer
        _isCasting.value = false

        // Resume local playback
        localExoPlayer?.let { exoPlayer ->
            currentMediaItem?.let { mediaItem ->
                exoPlayer.setMediaItem(mediaItem, playbackPosition)
                exoPlayer.prepare()
                if (wasPlaying) {
                    exoPlayer.play()
                }
            }
        }
    }

    fun getCurrentPlayer(): Player? = currentPlayer

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }

    fun release() {
        castContext?.sessionManager?.removeSessionManagerListener(
            sessionManagerListener,
            CastSession::class.java
        )
        castPlayer?.setSessionAvailabilityListener(null)
        castPlayer?.release()

        castPlayer = null
        currentPlayer = null
        localExoPlayer = null
    }
}