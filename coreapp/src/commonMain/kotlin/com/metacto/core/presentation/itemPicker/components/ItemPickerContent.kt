package com.metacto.core.presentation.itemPicker.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.inputFields.PrimaryTextInputField
import com.metacto.core.presentation.components.wheelPicker.WheelPickerDefaults
import com.metacto.core.presentation.components.wheelPicker.WheelTextPicker
import com.metacto.core.presentation.itemPicker.ItemPickerContract.Event
import com.metacto.core.presentation.itemPicker.ItemPickerContract.State
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.toDp
import com.metacto.coreApp.MR
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ItemPickerContent(
    state: State,
    textSearchInputFieldPadding: PaddingValues = PaddingValues(CoreTheme.spacings.itemPicker.searchFieldPadding),
    wheelTextStyle: TextStyle = CoreTheme.typography.itemPicker.textStyle,
    wheelTextColor: Color = CoreTheme.colors.itemPicker.textColor,
    selectorShape: Shape = CoreTheme.shapes.itemPicker.selectorShape,
    selectorColor: Color = CoreTheme.colors.itemPicker.selectorColor,
    selectorBorderWidth: Dp = CoreTheme.spacings.itemPicker.selectorBorderWidth,
    selectorBorderColor: Color = CoreTheme.colors.itemPicker.selectorBorderColor,
    wheelHeight: Dp = CoreTheme.spacings.itemPicker.wheelHeight,
    onEvent: (Event) -> Unit
) {
    var currentSelectedIndex = remember { state.initialItemIndex }
    var sheetSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    val itemTitles = remember(state.displayedItems) {
        state.displayedItems.map { it.title }
    }

    // Bottom sheet container
    BottomSheetContainer(
        startIcon = Icons.Default.Close,
        endIcon = Icons.Default.Check,
        onStartIconClick = {
            onEvent(Event.CloseClicked)
        },
        onEndIconClick = {
            onEvent(
                Event.DoneClicked(
                    selectedIndex = currentSelectedIndex
                )
            )
        },
        modifier = Modifier.onSizeChanged {
            sheetSize = it
        }
    ) {
        // Search field if required
        if (state.canSearch) {
            PrimaryTextInputField(
                text = state.searchTerm,
                placeholder = stringResource(MR.strings.search_here),
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
                    .padding(textSearchInputFieldPadding)
            )
        }

        // Wheel picker
        WheelTextPicker(
            startIndex = state.initialItemIndex,
            texts = itemTitles,
            rowCount = 5,
            style = wheelTextStyle,
            color = wheelTextColor,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                shape = selectorShape,
                color = selectorColor,
                border = BorderStroke(
                    width = selectorBorderWidth,
                    color = selectorBorderColor
                ),
            ),
            onScrollFinished = { index ->
                currentSelectedIndex = index
                null
            },
            size = DpSize(
                width = sheetSize.width.toDp(),
                height = wheelHeight
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(wheelHeight),
        )
    }
}