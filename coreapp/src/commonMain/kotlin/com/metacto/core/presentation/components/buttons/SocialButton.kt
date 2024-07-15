package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    textColor: Color = CoreTheme.colors.onPrimary,
    backgroundColor: Color = CoreTheme.colors.black,
    contentAlignment: Alignment.Horizontal = Alignment.Start,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.stroke,
        color = CoreTheme.colors.onPrimary
    ),
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textColor = textColor,
        textStyle = textStyle,
        startIconPainter = iconPainter,
        startIconVector = iconVector,
        backgroundColor = backgroundColor,
        isEnabled = isEnabled,
        isLoading = isLoading,
        onClick = onClick,
        iconColor = null,
        contentAlignment = contentAlignment,
        border = border,
        padding = padding
    )
}