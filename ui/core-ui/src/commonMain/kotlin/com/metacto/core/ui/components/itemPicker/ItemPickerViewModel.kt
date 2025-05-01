package com.metacto.core.ui.components.itemPicker

import com.metacto.core.PlatformType
import com.metacto.core.extensions.orZero
import com.metacto.core.ui.base.CoreViewModel
import com.metacto.core.ui.components.itemPicker.ItemPickerContract.Effect
import com.metacto.core.ui.components.itemPicker.ItemPickerContract.Event
import com.metacto.core.ui.components.itemPicker.ItemPickerContract.State
import com.metacto.core.ui.components.itemPicker.models.PickerItem

class ItemPickerViewModel : CoreViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(
            items = event.items,
            selectedItem = event.selectedItem,
            canSearch = event.canSearch,
            platform = event.platform
        )

        is Event.DoneClicked -> handleDoneClick()
        is Event.ScrollFinished -> handleScrollFinish(event.currentItemIndex)
        is Event.SearchTermChanged -> handleSearchTermChange(event.value)
        Event.ClearSearchClicked -> handleClearSearchClick()
        Event.SearchClicked -> handleSearchClick()
        Event.Disposed -> handleDispose()
    }

    private fun init(
        items: List<PickerItem>,
        selectedItem: PickerItem?,
        canSearch: Boolean,
        platform: PlatformType?
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Prepare initial item index
        val initialItemIndex = items
            .indexOfFirst { it.key == selectedItem?.key }
            .takeIf { it != -1 }
            .orZero()

        // Update state
        setState {
            copy(
                items = items,
                displayedItems = items,
                initialItemIndex = initialItemIndex,
                currentItemIndex = initialItemIndex,
                canSearch = canSearch,
                platform = platform
            )
        }

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    private fun handleDoneClick() {
        // Get selected item
        val selectedItem = currentState.displayedItems.getOrNull(currentState.currentItemIndex)

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

    private fun handleScrollFinish(currentItemIndex: Int) {
        setState {
            copy(currentItemIndex = currentItemIndex)
        }
    }

    private fun handleSearchTermChange(value: String) {
        // Stop if value didn't change
        if (value == currentState.searchTerm) return

        // Filter items
        val filteredItems = if (value.isBlank()) {
            currentState.items
        } else {
            currentState.items.filter { it.title.contains(value, ignoreCase = true) }
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

    private fun handleDispose() {
        emitSelectedItemAsResult()
    }

    override fun onDispose() {
        emitSelectedItemAsResult()
        super.onDispose()
    }

    private fun emitSelectedItemAsResult() {
        val selectedItem = currentState.displayedItems.getOrNull(currentState.currentItemIndex) ?: return
        navManager.sendResult(
            source = ItemPickerSheet::class.simpleName,
            result = selectedItem
        )
    }
}