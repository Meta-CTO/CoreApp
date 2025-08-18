package com.metacto.core.ui.components.options

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.components.options.models.OptionUIModel

class OptionsContract {

    data class State(
        val isInitialized: Boolean = false,
        val options: List<OptionUIModel> = emptyList()
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(val options: List<OptionUIModel>) : Event()
        data object CloseClicked : Event()
        data class OptionItemClicked(val item: OptionUIModel) : Event()
    }

    sealed class Effect : ViewSideEffect
}