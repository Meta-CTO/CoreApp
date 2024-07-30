package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.CoreViewModel
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Effect
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.ItemPickerContract.State
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.extensions.orZero
import kotlinx.collections.immutable.toImmutableList


class ItemPickerViewModel : CoreViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(
            items = event.items,
            selectedItem = event.selectedItem,
            canSearch = event.canSearch
        )

        Event.CloseClicked -> navManager.goBack()
        is Event.DoneClicked -> handleDoneClick(event.selectedIndex)
        is Event.SearchTermChanged -> handleSearchTermChange(event.value)
        Event.ClearSearchClicked -> handleClearSearchClick()
        Event.SearchClicked -> handleSearchClick()
    }

    private fun init(
        items: List<PickerItem>,
        selectedItem: PickerItem?,
        canSearch: Boolean
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Prepare initial item index
        val initialItemIndex = items
            .indexOfFirst { it.key == selectedItem?.key }
            .takeIf { it != -1 }
            .orZero()

        // Update state
        val immutableItems = items.toImmutableList()
        setState {
            copy(
                items = immutableItems,
                displayedItems = immutableItems,
                initialItemIndex = initialItemIndex,
                canSearch = canSearch
            )
        }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleDoneClick(selectedIndex: Int) {
        // Get selected item
        val selectedItem = currentState.displayedItems.getOrNull(selectedIndex)

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

    private fun handleSearchTermChange(value: String) {
        // Stop if value didn't change
        if (value == currentState.searchTerm) return

        // Filter items
        val filteredItems = if (value.isBlank()) {
            currentState.items
        } else {
            currentState.items
                .filter { it.title.contains(value, ignoreCase = true) }
                .toImmutableList()
        }

        // Update state
        setState {
            copy(
                searchTerm = value,
                displayedItems = filteredItems
            )
        }
    }

    private fun handleClearSearchClick() {
        // Dismiss keyboard
        coreGlobalState.dismissKeyboard()

        // Update state
        setState {
            copy(
                searchTerm = "",
                displayedItems = items
            )
        }
    }

    private fun handleSearchClick() {
        coreGlobalState.dismissKeyboard()
    }
}