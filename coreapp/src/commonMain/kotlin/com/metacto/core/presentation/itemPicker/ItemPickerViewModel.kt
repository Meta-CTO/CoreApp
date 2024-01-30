package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.itemPicker.ItemPickerContract.State
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Effect
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.extensions.orZero


class ItemPickerViewModel : CoreViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(
            items = event.items,
            selectedItem = event.selectedItem
        )

        Event.CloseClicked -> navManager.goBack()
        is Event.DoneClicked -> handleDoneClick(event.selectedIndex)
    }

    private fun init(
        items: List<PickerItem>,
        selectedItem: PickerItem?
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Prepare initial item index
        val initialItemIndex = items
            .indexOfFirst { it.key == selectedItem?.key }
            .orZero()

        // Update state
        setState {
            copy(
                items = items,
                initialItemIndex = initialItemIndex
            )
        }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleDoneClick(selectedIndex: Int) {
        // Get selected item
        val selectedItem = currentState.items.getOrNull(selectedIndex)

        // Check it
        if (selectedItem != null) {
            navManager.goBackWithResult(
                source = ItemPickerSheet::class.simpleName,
                result = selectedItem
            )
        } else {
            navManager.goBack()
        }
    }
}