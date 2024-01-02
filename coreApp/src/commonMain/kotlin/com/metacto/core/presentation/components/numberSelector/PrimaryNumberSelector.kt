package com.metacto.core.presentation.components.numberSelector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PrimaryNumberSelector(
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
            PrimaryNumberItem(
                number = number,
                isSelected = number == selectedNumber,
                onClick = {
                    onNumberClicked.invoke(number)
                }
            )
        }
    }
}