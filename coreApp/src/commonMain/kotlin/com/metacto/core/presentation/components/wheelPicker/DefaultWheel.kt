package com.metacto.core.presentation.components.wheelPicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.coroutines.launch

@Composable
fun DefaultWheel(
    modifier: Modifier = Modifier,
    items: List<*>,
    initialIndex: Int,
    onItemChange: (Int) -> Unit
) {
    // Prepare wheel state
    val wheelState = rememberFWheelPickerState(initialIndex)
    val coroutineScope = rememberCoroutineScope()

    // Wheel picker
    WheelPicker(
        state = wheelState,
        count = items.size,
        modifier = modifier,
        onIndexChanged = onItemChange,
        unfocusedCount = 3,
        isVertical = true,
        content = { index ->
            SingleLineText(
                text = items[index].toString(),
                textAlign = TextAlign.Center,
                style = CoreTheme.typography.bodyXLarge,
                color = CoreTheme.colors.secondary,
                onClick = {
                    coroutineScope.launch {
                        wheelState.animateScrollToIndex(index)
                    }
                }
            )
        }
    )
}