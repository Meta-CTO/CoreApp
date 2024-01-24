package com.metacto.core.presentation.options

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.options.OptionsContract.Effect
import com.metacto.core.presentation.options.OptionsContract.Event
import com.metacto.core.presentation.options.OptionsContract.State
import com.metacto.core.presentation.options.models.OptionUIModel


class OptionsViewModel : CoreViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(event.options)
        Event.CloseClicked -> navManager.goBack()
        is Event.OptionItemClicked -> handleOptionItemClick(event.item)
    }

    private fun init(options: List<OptionUIModel>) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Update state
        setState { copy(options = options) }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleOptionItemClick(item: OptionUIModel) {
        navManager.goBackWithResult(
            source = OptionsSheet::class.simpleName,
            result = item
        )
    }
}