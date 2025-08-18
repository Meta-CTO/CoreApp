package com.metacto.core.ui.components.numberSelector

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun SecondaryNumberItem(
    modifier: Modifier = Modifier,
    number: Int,
    isSelected: Boolean,
    selectedTextColor: Color = CoreTheme.colors.secondaryNumberItem.selectedTextColor,
    selectedBackgroundColor: Color = CoreTheme.colors.secondaryNumberItem.selectedBgColor,
    unSelectedTextColor: Color = CoreTheme.colors.secondaryNumberItem.unSelectedTextColor,
    unSelectedBackgroundColor: Color = CoreTheme.colors.secondaryNumberItem.unSelectedBgColor,
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