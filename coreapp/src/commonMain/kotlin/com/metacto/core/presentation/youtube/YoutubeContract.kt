package com.metacto.core.presentation.youtube

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class YoutubeContract {

    data class State(
        val isInitialized: Boolean = false,
        val videoId: String? = null,
        val isLandscape: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(val videoId: String) : Event()
        data class OrientationChanged(val isLandscape: Boolean) : Event()
        data object BackClicked : Event()
    }

    sealed class Effect : ViewSideEffect
}