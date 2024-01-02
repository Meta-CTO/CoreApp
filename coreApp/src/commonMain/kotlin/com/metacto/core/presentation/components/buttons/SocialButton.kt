package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    iconPainter: Painter? = null,
    iconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.btnPaddingVertical,
        horizontal = CoreTheme.spacings.btnPaddingHorizontal
    ),
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textColor = CoreTheme.colors.onPrimary,
        textStyle = textStyle,
        startIconPainter = iconPainter,
        startIconVector = iconVector,
        backgroundColor = CoreTheme.colors.black,
        isEnabled = isEnabled,
        isLoading = isLoading,
        onClick = onClick,
        iconColor = null,
        contentAlignment = Alignment.Start,
        border = BorderStroke(
            width = CoreTheme.spacings.stroke,
            color = CoreTheme.colors.onPrimary
        ),
        padding = padding
    )
}