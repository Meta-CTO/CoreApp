package com.metacto.core.presentation.components.numberSelector

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun SecondaryNumberItem(
    modifier: Modifier = Modifier,
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NumberItem(
        modifier = modifier,
        number = number,
        isSelected = isSelected,
        selectedTextColor = CoreTheme.colors.onSecondary,
        selectedBackgroundColor = CoreTheme.colors.secondary,
        unSelectedTextColor = CoreTheme.colors.secondary,
        unSelectedBackgroundColor = CoreTheme.colors.secondaryContainer,
        onClick = onClick
    )
}