package com.metacto.core.ui.components.itemPicker

import com.metacto.core.PlatformType
import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState
import com.metacto.core.ui.components.itemPicker.models.PickerItem

class ItemPickerContract {

    data class State(
        val isInitialized: Boolean = false,
        val items: List<PickerItem> = listOf(),
        val displayedItems: List<PickerItem> = listOf(),
        val initialItemIndex: Int = 0,
        val platform: PlatformType? = null,
        val currentItemIndex: Int = 0,
        val canSearch: Boolean = false,
        val searchTerm: String = "",
        val maxItemLines: Int = 1,
        val visibleItemCount: Int = 5
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(
            val items: List<PickerItem>,
            val selectedItem: PickerItem?,
            val canSearch: Boolean,
            val platform: PlatformType?,
            val maxItemLines: Int,
            val visibleItemCount: Int
        ) : Event()

        data object DoneClicked : Event()
        data class ScrollFinished(val currentItemIndex: Int) : Event()
        data class SearchTermChanged(val value: String) : Event()
        data object ClearSearchClicked : Event()
        data object SearchClicked : Event()
        data object Disposed : Event()
    }

    sealed class Effect : ViewSideEffect
}