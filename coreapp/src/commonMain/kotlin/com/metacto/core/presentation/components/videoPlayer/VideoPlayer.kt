package com.metacto.core.presentation.components.videoPlayer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.coreApp.resources.*
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
)