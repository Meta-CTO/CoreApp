package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PrimaryFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textColor: Color = CoreTheme.colors.onPrimary,
    iconColor: Color = CoreTheme.colors.onPrimary,
    backgroundColor: Color = CoreTheme.colors.primary,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
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
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        backgroundColor = backgroundColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        minHeight = if (isSmall) CoreTheme.spacings.btnMinHeightSmall else CoreTheme.spacings.btnMinHeightNormal,
        shape = if (isSmall) CoreTheme.shapes.xSmall else CoreTheme.shapes.small,
        padding = padding
    )
}
