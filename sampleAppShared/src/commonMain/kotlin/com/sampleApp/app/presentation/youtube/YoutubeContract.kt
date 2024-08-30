package com.sampleApp.app.presentation.youtube

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class YoutubeContract {

    data class State(
        val isInitialized: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data object CtaClicked : Event()
    }

    sealed class Effect : ViewSideEffect
}