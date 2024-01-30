package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.itemPicker.models.PickerItem

class ItemPickerContract {

    data class State(
        val isInitialized: Boolean = false,
        val items: List<PickerItem> = emptyList(),
        val initialItemIndex: Int = 0
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(
            val items: List<PickerItem>,
            val selectedItem: PickerItem?
        ) : Event()

        data object CloseClicked : Event()
        data class DoneClicked(val selectedIndex: Int) : Event()
    }

    sealed class Effect : ViewSideEffect
}