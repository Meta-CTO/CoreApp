package com.metacto.core.presentation.itemPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.components.ItemPickerContent
import com.metacto.core.presentation.itemPicker.models.PickerItem
import com.metacto.core.utils.PlatformType

actual class ItemPickerSheet actual constructor(
    actual val items: List<PickerItem>,
    actual val selectedItem: PickerItem?,
    actual val maxItemLines: Int,
    actual val visibleItemCount: Int,
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
                    maxItemLines = maxItemLines,
                    visibleItemCount = visibleItemCount
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