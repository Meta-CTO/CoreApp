package com.metacto.core.presentation.components.snackBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun SuccessSnackBar(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.ThumbUpOffAlt,
    onClick: (() -> Unit)? = null
) {
    AppSnackBar(
        modifier = modifier,
        background = CoreTheme.colors.successContainer,
        color = CoreTheme.colors.success,
        text = text,
        icon = icon,
        addStatusBarPadding = true,
        onClick = onClick
    )
}