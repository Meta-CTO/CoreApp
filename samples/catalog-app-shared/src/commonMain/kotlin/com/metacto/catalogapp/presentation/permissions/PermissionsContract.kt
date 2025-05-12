package com.metacto.catalogapp.presentation.permissions

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class PermissionsContract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
    }

    sealed class Effect : ViewSideEffect
}
