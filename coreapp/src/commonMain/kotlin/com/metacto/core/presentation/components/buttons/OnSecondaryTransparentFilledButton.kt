package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun OnSecondaryTransparentFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.onSecondaryTransparentFilledButton.textStyle,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    iconSize: Dp = CoreTheme.spacings.iconLarge,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.onSecondaryTransparentFilledButton.paddingVertical,
        horizontal = CoreTheme.spacings.onSecondaryTransparentFilledButton.paddingHorizontal
    ),
    backgroundColor: Color = CoreTheme.colors.onSecondaryTransparentFilledButton.bgColor,
    textColor: Color = CoreTheme.colors.onSecondaryTransparentFilledButton.textColor,
    iconColor: Color = CoreTheme.colors.onSecondaryTransparentFilledButton.iconColor,
    elevation: Dp = CoreTheme.spacings.onSecondaryTransparentFilledButton.elevation,
    minHeight: Dp = CoreTheme.spacings.onSecondaryTransparentFilledButton.minHeight,
    shape: RoundedCornerShape = CoreTheme.shapes.onSecondaryTransparentFilledButton.shape,
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        elevation = elevation,
        backgroundColor = backgroundColor,
        disabledBackgroundColor = backgroundColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        iconSize = iconSize,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        minHeight = minHeight,
        shape = shape,
        padding = padding
    )
}