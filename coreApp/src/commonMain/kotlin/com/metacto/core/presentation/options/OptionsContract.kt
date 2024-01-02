package com.metacto.core.presentation.options

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.options.models.OptionUIModel

class OptionsContract {

    data class State(
        val isInitialized: Boolean = false,
        val options: List<OptionUIModel> = emptyList()
    ) : ViewState

    sealed class Event : ViewEvent {
        data object CloseClicked : Event()
        data class OptionItemClicked(val item: OptionUIModel) : Event()
    }

    sealed class Effect : ViewSideEffect
}