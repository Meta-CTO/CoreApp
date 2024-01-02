package com.metacto.core.presentation.components.numberSelector

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PrimaryNumberItem(
    modifier: Modifier = Modifier,
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NumberItem(
        modifier = modifier,
        number = number,
        isSelected = isSelected,
        selectedTextColor = CoreTheme.colors.onStrongDarkPrimary,
        selectedBackgroundColor = CoreTheme.colors.primaryStrongDark,
        unSelectedTextColor = CoreTheme.colors.secondary,
        unSelectedBackgroundColor = CoreTheme.colors.primary,
        onClick = onClick
    )
}