package com.metacto.core.presentation.itemPicker

import com.metacto.core.presentation.base.ViewEvent
import com.metacto.core.presentation.base.ViewSideEffect
import com.metacto.core.presentation.base.ViewState
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.extensions.getPlatformType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ItemPickerContract {

    data class State(
        val isInitialized: Boolean = false,
        val items: ImmutableList<PickerItem> = persistentListOf(),
        val displayedItems: ImmutableList<PickerItem> = persistentListOf(),
        val initialItemIndex: Int = 0,
        val platform: PlatformType? = null,
        val currentItemIndex: Int = 0,
        val canSearch: Boolean = false,
        val searchTerm: String = ""
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(
            val items: List<PickerItem>,
            val selectedItem: PickerItem?,
            val canSearch: Boolean,
            val platform: PlatformType?
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