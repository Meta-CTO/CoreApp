package com.metacto.core.presentation.itemPicker.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import com.metacto.core.presentation.components.bottomSheets.BottomSheetDoneContainer
import com.metacto.core.presentation.components.inputFields.PrimaryTextInputField
import com.metacto.core.presentation.components.wheelPicker.WheelPickerDefaults
import com.metacto.core.presentation.components.wheelPicker.WheelTextPicker
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.ItemPickerContract.State
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.PlatformType
import com.metacto.core.utils.extensions.getPlatformType
import com.metacto.core.utils.extensions.toDp
import com.metacto.coreApp.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun ItemPickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {

    // check the platform type
    val platform = state.platform ?: getPlatformType()

    // ini the theme colors  based on the platform
    val colorTheme = if (platform == PlatformType.ANDROID)
        CoreTheme.colors.itemPicker
    else
        CoreTheme.colors.iosItemPicker

    // ini the wheel picker row count based on the platform
    val wheelSelectorShape = if (platform == PlatformType.ANDROID)
        CoreTheme.shapes.wheelPickerItem
    else
        CoreTheme.shapes.iosWheelPickerItem

    var sheetSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val itemTitles = remember(state.displayedItems) {
        state.displayedItems.map { it.title }
    }

    // Bottom sheet container
    BottomSheetDoneContainer(
        platform = platform,
        onDoneClick = {
            onEvent(Event.DoneClicked)
        },
        modifier = Modifier.onSizeChanged {
            sheetSize = it
        }
    ) {
        // Search field if required
        if (state.canSearch) {
            PrimaryTextInputField(
                text = state.searchTerm,
                placeholder = stringResource(Res.string.search_here),
                startIconVector = Icons.Default.Search,
                endIconVector = Icons.Default.Close,
                imeAction = ImeAction.Search,
                keyboardActions = KeyboardActions(onSearch = {
                    onEvent(Event.SearchClicked)
                }),
                onValueChange = {
                    onEvent(Event.SearchTermChanged(it))
                },
                onEndIconClick = {
                    onEvent(Event.ClearSearchClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CoreTheme.spacings.itemPicker.searchFieldPadding)
            )
        }

        // Wheel picker
        WheelTextPicker(
            startIndex = state.initialItemIndex,
            texts = itemTitles,
            rowCount = 5,
            style = CoreTheme.typography.itemPicker.textStyle,
            color = colorTheme.textColor,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = colorTheme.selectorColor,
                border = BorderStroke(
                    width = CoreTheme.spacings.itemPicker.selectorBorderWidth,
                    color = colorTheme.selectorBorderColor
                ),
                shape = wheelSelectorShape
            ),
            onScrollFinished = { index ->
                onEvent(Event.ScrollFinished(index))
                null
            },
            size = DpSize(
                width = sheetSize.width.toDp(),
                height = CoreTheme.spacings.itemPicker.wheelHeight
            ),
            onItemClicked = {
                onEvent(Event.DoneClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(CoreTheme.spacings.itemPicker.wheelHeight),
        )
    }
}