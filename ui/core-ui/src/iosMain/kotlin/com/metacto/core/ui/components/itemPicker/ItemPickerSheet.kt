package com.metacto.core.ui.components.itemPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.metacto.core.PlatformType
import com.metacto.core.ui.base.CoreSheet
import com.metacto.core.ui.base.getViewModel
import com.metacto.core.ui.components.itemPicker.ItemPickerContract.Event
import com.metacto.core.ui.components.itemPicker.components.ItemPickerContent
import com.metacto.core.ui.components.itemPicker.models.PickerItem

actual class ItemPickerSheet actual constructor(
    actual val items: List<PickerItem>,
    actual val selectedItem: PickerItem?,
    actual val maxItemLines: Int,
    actual val visibleItemCount: Int,
    actual val canSearch: Boolean,
    actual val platform: PlatformType?
) : CoreSheet<ItemPickerViewModel>() {

    private val viewModel = getViewModel<ItemPickerViewModel>()

    init {
        // Init view model
        viewModel.setEvent(
            Event.Init(
                items = items,
                selectedItem = selectedItem,
                canSearch = canSearch,
                platform = platform,
                maxItemLines = maxItemLines,
                visibleItemCount = visibleItemCount
            )
        )
    }

    @Composable
    override fun Content() {
        // Render content
        ItemPickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )

        // Handle lifecycle
        DisposableEffect(Unit) {
            onDispose {
                viewModel.setEvent(Event.Disposed)
            }
        }
    }
}