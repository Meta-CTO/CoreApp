package com.metacto.core.presentation.itemPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.getViewModel
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.components.ItemPickerContent
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.PlatformType

actual class ItemPickerSheet actual constructor(
    actual val items: List<PickerItem>,
    actual val selectedItem: PickerItem?,
    actual val canSearch: Boolean,
    actual val platform: PlatformType?
) : BaseSheet<ItemPickerViewModel>() {

    private val viewModel = getViewModel<ItemPickerViewModel>()

    init {
        // Init view model
        viewModel.setEvent(
            Event.Init(
                items = items,
                selectedItem = selectedItem,
                canSearch = canSearch,
                platform = platform
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