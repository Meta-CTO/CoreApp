package com.metacto.core.presentation.components.videoPlayer

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.cast.CastPlayer
import androidx.media3.cast.SessionAvailabilityListener
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.CastState
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.Executors

/**
 * Manages cast functionality for video playback
 */
@OptIn(UnstableApi::class)
class CastManager(private val context: Context) {

    private var castPlayer: CastPlayer? = null
    private var castContext: CastContext? = null
    private var currentPlayer: Player? = null
    private var localExoPlayer: ExoPlayer? = null

    private var currentMediaItem: MediaItem? = null
    private var currentUri: String? = null
    private var playbackPosition: Long = 0
    private var wasPlaying: Boolean = false

    private val _castAvailable = MutableStateFlow(false)
    val castAvailable: StateFlow<Boolean> = _castAvailable.asStateFlow()

    private val _isCasting = MutableStateFlow(false)
    val isCasting: StateFlow<Boolean> = _isCasting.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val sessionManagerListener = object : SessionManagerListener<CastSession> {
        override fun onSessionStarting(session: CastSession) {
            // Session is starting
        }

        override fun onSessionStarted(session: CastSession, sessionId: String) {
            onCastSessionStarted(session)
        }

        override fun onSessionStartFailed(session: CastSession, error: Int) {
            _isCasting.value = false
            _errorState.value = "Cast session failed to start"
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
            onCastSessionStarted(session)
        }

        override fun onSessionResumeFailed(session: CastSession, error: Int) {
            _isCasting.value = false
            _errorState.value = "Cast session failed to resume"
        }

        override fun onSessionSuspended(session: CastSession, reason: Int) {
            _isCasting.value = false
        }
    }

    init {
        initializeCast()
    }

    private fun initializeCast() {
        if (!isGooglePlayServicesAvailable()) {
            _castAvailable.value = false
            _isCasting.value = false
            _errorState.value = "Google Play Services not available"
            _isInitialized.value = false
            return
        }

        // Use the non-deprecated method that returns a Task
        val executor = Executors.newSingleThreadExecutor()
        val castContextTask = CastContext.getSharedInstance(context, executor)

        castContextTask.addOnSuccessListener { castContext ->
            // Handle successful CastContext initialization
            this.castContext = castContext

            // Add listener for cast state changes
            castContext.addCastStateListener { state ->
                _castAvailable.value = state != CastState.NO_DEVICES_AVAILABLE
            }

            // Create cast player
            val player = CastPlayer(castContext)
            castPlayer = player

            // Add cast session manager listener
            castContext.sessionManager.addSessionManagerListener(
                sessionManagerListener,
                CastSession::class.java
            )

            // Check initial cast state
            _castAvailable.value = castContext.castState != CastState.NO_DEVICES_AVAILABLE

            // Check if already casting
            val castSession = castContext.sessionManager.currentCastSession
            if (castSession != null && castSession.isConnected) {
                onCastSessionStarted(castSession)
            }

            _errorState.value = null
            _isInitialized.value = true
        }

        castContextTask.addOnFailureListener { exception ->
            _castAvailable.value = false
            _isCasting.value = false
            _errorState.value = "Failed to initialize cast: ${exception.message}"
            _isInitialized.value = false
        }
    }

    /**
     * Set up a cast button that can be inflated into the layout
     */
    fun setupCastButton(mediaRouteButton: androidx.mediarouter.app.MediaRouteButton): Boolean {
        return runCatching {
            castContext?.let {
                CastButtonFactory.setUpMediaRouteButton(context, mediaRouteButton)
                true
            } ?: false
        }.getOrDefault(false)
    }

    /**
     * Set the local ExoPlayer instance for playback transfer
     */
    fun setLocalPlayer(exoPlayer: ExoPlayer) {
        localExoPlayer = exoPlayer

        // If we're not casting, set the current player to the local player
        if (!_isCasting.value) {
            currentPlayer = exoPlayer
        }
    }

    /**
     * Start casting the current media
     */
    fun startCasting(mediaItem: MediaItem): Boolean {
        if (castContext == null) {
            _errorState.value = "Cast not initialized"
            return false
        }

        currentMediaItem = mediaItem
        currentUri = mediaItem.mediaId

        // Save the current playback position and state
        localExoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            wasPlaying = exoPlayer.isPlaying
        }

        // Create a launch intent for the Cast session
        val sessionIntent = Intent(context, context.javaClass)

        return runCatching {
            castContext?.sessionManager?.startSession(sessionIntent)
            true
        }.getOrDefault(false)
    }

    /**
     * Stop casting and return to local playback
     */
    fun stopCasting(): Boolean {
        return runCatching {
            castContext?.sessionManager?.endCurrentSession(true)
            true
        }.getOrDefault(false)
    }

    /**
     * Handle cast session started
     */
    private fun onCastSessionStarted(castSession: CastSession) {
        // Save the local player state
        localExoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            wasPlaying = exoPlayer.isPlaying
            if (currentMediaItem == null) {
                currentMediaItem = exoPlayer.currentMediaItem
            }
            if (currentUri == null && currentMediaItem != null) {
                currentUri = currentMediaItem?.mediaId
            }

            // Pause local playback
            exoPlayer.pause()
        }

        // Set up the cast player
        castPlayer?.setSessionAvailabilityListener(object : SessionAvailabilityListener {
            override fun onCastSessionAvailable() {
                transferPlaybackToCast(castSession)
            }

            override fun onCastSessionUnavailable() {
                _isCasting.value = false
            }
        })

        // Set current player to cast player
        currentPlayer = castPlayer
        _isCasting.value = true

        // Transfer playback if media item is available
        if (currentMediaItem != null || currentUri != null) {
            transferPlaybackToCast(castSession)
        }
    }

    /**
     * Handle cast session ended
     */
    private fun onCastSessionEnded() {
        // Set current player back to local player
        currentPlayer = localExoPlayer
        _isCasting.value = false

        // Resume local playback
        localExoPlayer?.let { exoPlayer ->
            currentMediaItem?.let { mediaItem ->
                runCatching {
                    exoPlayer.setMediaItem(mediaItem, playbackPosition)
                    exoPlayer.prepare()
                    if (wasPlaying) {
                        exoPlayer.play()
                    }
                }
            }
        }
    }

    /**
     * Transfer playback to cast device
     * Returns true if transfer was successful
     */
    private fun transferPlaybackToCast(castSession: CastSession): Boolean {
        val remoteMediaClient = castSession.remoteMediaClient ?: return false

        // Try to load via Media3 CastPlayer first
        val castTransferResult = runCatching {
            castPlayer?.let { player ->
                currentMediaItem?.let { mediaItem ->
                    player.setMediaItem(mediaItem, playbackPosition)
                    player.prepare()
                    if (wasPlaying) {
                        player.play()
                    }
                    return true
                }
            }
            false
        }.getOrDefault(false)

        if (castTransferResult) {
            return true
        }

        // Fallback to legacy Cast API if Media3 approach fails
        return runCatching {
            currentUri?.let { uri ->
                val mediaInfo = createMediaInfo(uri)
                val loadRequestData = MediaLoadRequestData.Builder()
                    .setMediaInfo(mediaInfo)
                    .setAutoplay(wasPlaying)
                    .setCurrentTime(playbackPosition)
                    .build()

                remoteMediaClient.load(loadRequestData)
                true
            } ?: false
        }.getOrDefault(false)
    }

    /**
     * Create MediaInfo for legacy Cast API
     */
    private fun createMediaInfo(uri: String): MediaInfo {
        val contentType = getContentType(uri)

        return MediaInfo.Builder(uri)
            .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
            .setContentType(contentType)
            .build()
    }

    /**
     * Get content type from URI
     */
    private fun getContentType(uri: String): String {
        return when {
            uri.contains(".mp4") -> "video/mp4"
            uri.contains(".m3u8") -> "application/x-mpegURL"
            uri.contains(".mpd") -> "application/dash+xml"
            uri.contains(".webm") -> "video/webm"
            else -> "video/mp4" // Default
        }
    }

    /**
     * Get the current player for playback control (local or cast)
     */
    fun getCurrentPlayer(): Player? {
        return currentPlayer
    }

    /**
     * Check if Google Play Services is available
     */
    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }

    /**
     * Seek to position in the current player
     */
    fun seekTo(positionMs: Long): Boolean {
        return runCatching {
            currentPlayer?.seekTo(positionMs)
            true
        }.getOrDefault(false)
    }

    /**
     * Set playback speed
     */
    fun setPlaybackSpeed(speed: Float): Boolean {
        return runCatching {
            currentPlayer?.setPlaybackSpeed(speed)
            true
        }.getOrDefault(false)
    }

    /**
     * Set volume
     */
    fun setVolume(volume: Float): Boolean {
        return runCatching {
            currentPlayer?.volume = volume
            true
        }.getOrDefault(false)
    }

    /**
     * Get current position
     */
    fun getCurrentPosition(): Long {
        return currentPlayer?.currentPosition ?: 0
    }

    /**
     * Get content duration
     */
    fun getDuration(): Long {
        return currentPlayer?.duration ?: 0
    }

    /**
     * Check if player is playing
     */
    fun isPlaying(): Boolean {
        return currentPlayer?.isPlaying ?: false
    }

    /**
     * Get buffered position
     */
    fun getBufferedPosition(): Long {
        return currentPlayer?.bufferedPosition ?: 0
    }

    /**
     * Get buffered percentage
     */
    fun getBufferedPercentage(): Int {
        return currentPlayer?.bufferedPercentage ?: 0
    }

    /**
     * Clear error state
     */
    fun clearErrorState() {
        _errorState.value = null
    }

    /**
     * Release resources
     */
    fun release() {
        runCatching {
            castContext?.sessionManager?.removeSessionManagerListener(
                sessionManagerListener,
                CastSession::class.java
            )
            castPlayer?.setSessionAvailabilityListener(null)
            castPlayer?.release()
        }

        castPlayer = null
        currentPlayer = null
        localExoPlayer = null
    }
}