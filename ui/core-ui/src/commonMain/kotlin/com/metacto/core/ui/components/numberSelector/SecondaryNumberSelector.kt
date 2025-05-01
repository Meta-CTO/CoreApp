package com.metacto.core.ui.components.numberSelector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SecondaryNumberSelector(
    modifier: Modifier = Modifier,
    numbers: IntRange,
    selectedNumber: Int?,
    onNumberClicked: (Int) -> Unit
) {
    // Container row
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Render numbers
        numbers.forEach { number ->
            SecondaryNumberItem(
                number = number,
                isSelected = number == selectedNumber,
                onClick = {
                    onNumberClicked.invoke(number)
                }
            )
        }
    }
}