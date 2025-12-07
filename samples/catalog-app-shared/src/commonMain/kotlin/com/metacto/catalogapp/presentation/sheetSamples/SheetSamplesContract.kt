package com.metacto.catalogapp.presentation.sheetSamples

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class SheetSamplesContract {

    data class State(
        val isInitialized: Boolean = false,
        val resultText: String = ""
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data class OnSheetResult(val result: String) : Event()
    }

    sealed class Effect : ViewSideEffect
}
