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
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import androidx.mediarouter.app.MediaRouteButton
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

    private val trackSelector = DefaultTrackSelector(context).apply {
        parameters = DefaultTrackSelector.Parameters.Builder(context!!)
            .setPreferredTextLanguage("en")
            .build()
    }

    private val _castAvailable = MutableStateFlow(false)
    val castAvailable: StateFlow<Boolean> = _castAvailable.asStateFlow()

    private val _isCasting = MutableStateFlow(false)
    val isCasting: StateFlow<Boolean> = _isCasting.asStateFlow()

    var onVideoLoop: (() -> Unit)? = null
    var onVideoEnd: (() -> Unit)? = null
    var isPipEnabled: Boolean = true
    private var videoSizeListener: ((width: Int, height: Int) -> Unit)? = null

    val exoPlayer by lazy {
        ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .setSeekBackIncrementMs(10_000L)
            .setSeekForwardIncrementMs(10_000L)
            .build()
    }

    private val mediaSession by lazy {
        MediaSession.Builder(context, exoPlayer).run {
            this.setId(uniqueId)
            context.getLauncherPendingIntent()?.let {
                setSessionActivity(it)
            }
            build()
        }
    }

    private val notificationManager by lazy {
        MediaNotificationManager(
            context = context,
            sessionToken = mediaSession.token,
            player = exoPlayer
        )
    }

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isMediaMetadataEnabled.not()) return
                if (isPlaying.not()) return

                playerManagers.values.forEach {
                    if (it.uniqueId == uniqueId) return@forEach
                    it.exoPlayer.pause()
                    it.notificationManager.hideNotification()
                }

                notificationManager.showNotificationForPlayer(exoPlayer)
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                if (reason == Player.DISCONTINUITY_REASON_AUTO_TRANSITION) {
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

            override fun onTracksChanged(tracks: androidx.media3.common.Tracks) {
                updateSubtitleTracks()
            }
        })

        initCastManager()
    }

    private fun initCastManager() {
        castManager = CastManager(context).apply {
            setLocalPlayer(exoPlayer)
            _castAvailable.value = castAvailable.value
            _isCasting.value = isCasting.value
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
        if (exoPlayer.currentMediaItem?.mediaId == videoUrl) {
            return
        }

        exoPlayer.apply {
            val mediaMetaData = MediaMetadata.Builder()
                .setTitle(videoTitle.orEmpty())
                .setArtist(videoArtist.orEmpty())
                .setAlbumArtist(videoArtist.orEmpty())
                .setArtworkUri(videoArtworkUrl?.toUri())
                .build()

            val mediaSource = createMediaSource(
                context = context,
                url = videoUrl,
                metaData = mediaMetaData
            )

            if (isAutoPlay) {
                playWhenReady = true
            }
            setMediaSource(mediaSource)
            prepare()
        }
    }

    fun addExternalSubtitle(language: String, fileName: String, content: String) {
        val uniqueFileName = "${System.currentTimeMillis()}_$fileName"

        val file = java.io.File(context.cacheDir, uniqueFileName)
        file.writeText(content, Charsets.UTF_8)

        if (!file.exists() || file.length() == 0.toLong()) {
            return
        }

        val authority = "${context.packageName}.fileprovider"

        val subtitleLoader = SubtitleFileLoader(context)
        val mimeType = subtitleLoader.getMimeTypeFromFileName(fileName)

        val subtitleUri = androidx.core.content.FileProvider.getUriForFile(
            context,
            authority,
            file
        )

        context.grantUriPermission(
            context.packageName,
            subtitleUri,
            android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
        )

        val currentItem = exoPlayer.currentMediaItem ?: return
        val mediaMetadata = currentItem.mediaMetadata

        val newSubtitleConfig = MediaItem.SubtitleConfiguration.Builder(subtitleUri)
            .setMimeType(mimeType)
            .setLanguage(language)
            .setLabel(fileName)
            .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
            .build()

        val currentConfigs = ArrayList(currentItem.localConfiguration?.subtitleConfigurations ?: emptyList())
        currentConfigs.add(newSubtitleConfig)

        val updatedItem = MediaItem.Builder()
            .setUri(currentItem.localConfiguration?.uri)
            .setMediaId(currentItem.mediaId)
            .setMediaMetadata(mediaMetadata)
            .setSubtitleConfigurations(currentConfigs)
            .build()

        val currentPosition = exoPlayer.currentPosition
        exoPlayer.setMediaItem(updatedItem, currentPosition)
        exoPlayer.prepare()

        updateSubtitleTracks()
        val parameters = trackSelector.parameters.buildUpon()
            .setPreferredTextLanguage(language)
            .setRendererDisabled(C.TRACK_TYPE_TEXT, false)
            .setSelectUndeterminedTextLanguage(true)
            .build()

        trackSelector.setParameters(parameters)
    }

    private fun updateSubtitleTracks() {
        val tracks = exoPlayer.currentTracks
        val subtitleTracks = mutableListOf<SubtitleTrack>()

        subtitleTracks.add(SubtitleTrack("none", "None", false))

        var hasSelectedTrack = false

        for (group in tracks.groups) {
            if (group.type == C.TRACK_TYPE_TEXT) {
                for (i in 0 until group.length) {
                    val format = group.getTrackFormat(i)
                    val language = format.language ?: "unknown"
                    val label = format.label ?: language.uppercase()
                    val isSelected = group.isTrackSelected(i)

                    val track = SubtitleTrack(language, label, isSelected)
                    subtitleTracks.add(track)

                    if (isSelected) {
                        hasSelectedTrack = true
                    }
                }
            }
        }

        if (subtitleTracks.size > 1 && !hasSelectedTrack) {
            val firstTrack = subtitleTracks.firstOrNull { it.languageCode != "none" }
            if (firstTrack != null) {
                selectSubtitleTrack(firstTrack)
            }
        }
    }

    private fun selectSubtitleTrack(track: SubtitleTrack?) {
        val parameters = if (track == null || track.languageCode == "none") {
            trackSelector.parameters.buildUpon()
                .setPreferredTextLanguage(null)
                .setRendererDisabled(C.TRACK_TYPE_TEXT, true)
                .build()
        } else {
            trackSelector.parameters.buildUpon()
                .setPreferredTextLanguage(track.languageCode)
                .setRendererDisabled(C.TRACK_TYPE_TEXT, false)
                .setSelectUndeterminedTextLanguage(true)
                .build()
        }

        trackSelector.setParameters(parameters)

        val currentPos = exoPlayer.currentPosition
        exoPlayer.seekTo(currentPos)
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

    fun setupCastButton(mediaRouteButton: MediaRouteButton) {
        castManager?.setupCastButton(mediaRouteButton)
    }

    fun setMediaMetadataEnabled(isEnabled: Boolean) {
        isMediaMetadataEnabled = isEnabled
    }
}

data class SubtitleTrack(
    val languageCode: String,
    val displayName: String,
    val isSelected: Boolean
)