package com.metacto.core.ui.components.itemPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.PlatformType
import com.metacto.core.ui.base.BaseSheet
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.components.itemPicker.components.ItemPickerContent
import com.metacto.core.ui.components.itemPicker.models.PickerItem
import com.metacto.core.ui.components.itemPicker.ItemPickerContract.Event

actual class ItemPickerSheet actual constructor(
    actual val items: List<PickerItem>,
    actual val selectedItem: PickerItem?,
    actual val canSearch: Boolean,
    actual val platform: PlatformType?
) : BaseSheet<ItemPickerViewModel>() {

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<ItemPickerViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                Event.Init(
                    items = items,
                    selectedItem = selectedItem,
                    canSearch = canSearch,
                    platform = platform,
                )
            )
        }

        // Render content
        ItemPickerContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}