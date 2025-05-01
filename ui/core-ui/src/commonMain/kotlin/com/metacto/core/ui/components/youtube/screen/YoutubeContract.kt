package com.metacto.core.ui.components.youtube.screen

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class YoutubeContract {

    data class State(
        val isInitialized: Boolean = false,
        val videoId: String? = null,
        val showControls: Boolean = false,
        val showFullScreenButton: Boolean = false,
        val shouldAutoPlay: Boolean = false,
        val isLandscape: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(val videoId: String, val shouldAutoPlay: Boolean, val showControls: Boolean, val showFullScreenButton: Boolean) : Event()
        data class OrientationChanged(val isLandscape: Boolean) : Event()
        data object BackClicked : Event()
    }

    sealed class Effect : ViewSideEffect
}