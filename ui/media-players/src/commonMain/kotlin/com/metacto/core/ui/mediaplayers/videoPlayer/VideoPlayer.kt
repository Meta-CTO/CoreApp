package com.metacto.core.ui.mediaplayers.videoPlayer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.mediaplayers.resources.Res
import com.metacto.core.ui.mediaplayers.resources.ic_pause
import com.metacto.core.ui.mediaplayers.resources.ic_play
import com.metacto.core.ui.theme.CoreTheme
import org.jetbrains.compose.resources.DrawableResource
import kotlin.time.Duration

interface VideoPlayerController {
    fun play() {}
    fun pause() {}
}

@Composable
expect fun VideoPlayer(
    modifier: Modifier = Modifier,
    uniqueId: String = "default_player_id",
    videoUrl: String,
    videoArtist: String? = null,
    videoTitle: String? = null,
    videoArtworkUrl: String? = null,
    autoPlay: Boolean = false,
    scaleToCrop: Boolean = false,
    enablePip: Boolean = false,
    enableMediaMetadata: Boolean = true,
    enableVoice: Boolean = true,
    autoRepeat:Boolean = false,
    handleLifecyclePause: Boolean = true,
    controllerShowTimeoutMs: Int = 0,
    controlsType: ControlsType = ControlsType.NativeControls,
    playIconRes: DrawableResource = Res.drawable.ic_play,
    pauseIconRes: DrawableResource = Res.drawable.ic_pause,
    customControlsSize: Dp = CoreTheme.spacings.videoPlayer.customIconsSize,
    customControlsElevation: Dp = CoreTheme.spacings.videoPlayer.customIconsElevation,
    customControlsShape: RoundedCornerShape = CoreTheme.shapes.videoPlayer.customIconShape,
    onPlayerCreated: ((VideoPlayerController) -> Unit)? = null,
    onDurationCaught: ((Duration) -> Unit)? = null,
    onVideoLoop: (() -> Unit)? = null,
    onVideoEnd: (() -> Unit)? = null
)