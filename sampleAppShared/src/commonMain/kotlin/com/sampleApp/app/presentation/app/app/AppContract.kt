package com.sampleApp.app.presentation.app.app

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class AppContract {
    object State : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
    }

    sealed class Effect : ViewSideEffect
}
