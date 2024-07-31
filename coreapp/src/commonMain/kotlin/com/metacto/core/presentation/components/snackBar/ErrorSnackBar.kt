package com.metacto.core.presentation.components.snackBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun ErrorSnackBar(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.ErrorOutline,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = CoreTheme.colors.errorSnackBarBgColor,
    color: Color = CoreTheme.colors.errorSnackBarColor
) {
    AppSnackBar(
        modifier = modifier,
        background = backgroundColor,
        color = color,
        text = text,
        icon = icon,
        addStatusBarPadding = true,
        onClick = onClick
    )
}