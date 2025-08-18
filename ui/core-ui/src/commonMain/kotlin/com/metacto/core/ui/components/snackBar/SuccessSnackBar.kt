package com.metacto.core.ui.components.snackBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun SuccessSnackBar(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.ThumbUpOffAlt,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = CoreTheme.colors.successSnackBar.bgColor,
    color: Color = CoreTheme.colors.successSnackBar.color
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