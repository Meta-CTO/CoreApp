package com.metacto.core.presentation.itemPicker

import androidx.compose.runtime.Composable
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.getViewModel
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.components.ItemPickerContent
import com.metacto.core.presentation.itemPicker.models.PickerItem

actual class ItemPickerSheet actual constructor(
    actual val items: List<PickerItem>,
    actual val selectedItem: PickerItem?
) : BaseSheet<ItemPickerViewModel>() {

    private val viewModel = getViewModel<ItemPickerViewModel>()

    init {
        // Init view model
        viewModel.setEvent(
            Event.Init(
                items = items,
                selectedItem = selectedItem
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
    }
}