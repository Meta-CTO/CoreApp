package com.metacto.core.ui.components.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.floatingButton.textStyle,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    loadingColor: Color = CoreTheme.colors.floatingButton.loadingColor,
    disabledBgColor: Color = CoreTheme.colors.floatingButton.disabledBgColor,
    textColor: Color = CoreTheme.colors.floatingButton.textColor,
    iconColor: Color = CoreTheme.colors.floatingButton.iconColor,
    iconSize: Dp = CoreTheme.spacings.floatingButton.iconSize,
    backgroundColor: Color = CoreTheme.colors.floatingButton.bgColor,
    minHeight: Dp = CoreTheme.spacings.floatingButton.minHeight,
    contentSpacing: Dp = CoreTheme.spacings.floatingButton.contentSpacing,
    shape: RoundedCornerShape = CoreTheme.shapes.floatingButton.shape,
    elevation: Dp = CoreTheme.spacings.floatingButton.elevation,
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        iconSize = iconSize,
        backgroundColor = backgroundColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        disabledBackgroundColor = disabledBgColor,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        loadingColor = loadingColor,
        onClick = onClick,
        minHeight = minHeight,
        contentSpacing = contentSpacing,
        shape = shape,
        elevation = elevation
    )
}