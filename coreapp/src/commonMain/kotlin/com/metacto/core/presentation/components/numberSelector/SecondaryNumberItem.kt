package com.metacto.core.presentation.components.numberSelector

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun SecondaryNumberItem(
    modifier: Modifier = Modifier,
    number: Int,
    isSelected: Boolean,
    selectedTextColor: Color = CoreTheme.colors.secondaryNumberItemSelectedTextColor,
    selectedBackgroundColor: Color = CoreTheme.colors.secondaryNumberItemSelectedBgColor,
    unSelectedTextColor: Color = CoreTheme.colors.secondaryNumberItemUnSelectedTextColor,
    unSelectedBackgroundColor: Color = CoreTheme.colors.secondaryNumberItemUnSelectedBgColor,
    onClick: () -> Unit
) {
    NumberItem(
        modifier = modifier,
        number = number,
        isSelected = isSelected,
        selectedTextColor = selectedTextColor,
        selectedBackgroundColor = selectedBackgroundColor,
        unSelectedTextColor = unSelectedTextColor,
        unSelectedBackgroundColor = unSelectedBackgroundColor,
        onClick = onClick
    )
}