package com.metacto.core.presentation.youtube

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

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