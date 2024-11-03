package com.sampleApp.app.presentation.profile

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState

class ProfileContract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object NativeItemPicker : Event()
    }

    sealed class Effect : ViewSideEffect
}
