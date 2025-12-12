package com.metacto.catalogapp.presentation.customDialog

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class CustomDialogSamplesContract {

    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object ShowSimpleDialog : Event()
        data object ShowDialogWithToolbar : Event()
        data object ShowDialogWithButton : Event()
        data object ShowDialogWithForm : Event()
        data object ShowComplexDialog : Event()
    }

    sealed class Effect : ViewSideEffect
}
