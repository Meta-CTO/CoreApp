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
fun DangerFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.dangerFilledButton.textStyle,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    iconSize: Dp = CoreTheme.spacings.dangerFilledButton.iconSize,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.dangerFilledButton.paddingVertical,
        horizontal = CoreTheme.spacings.dangerFilledButton.paddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.dangerFilledButton.textColor,
    iconColor: Color = CoreTheme.colors.dangerFilledButton.iconColor,
    backgroundColor: Color = CoreTheme.colors.dangerFilledButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.dangerFilledButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.dangerFilledButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.dangerFilledButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.dangerFilledButton.shapeNormal,
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
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        minHeight = if (isSmall) minHeightSmall else minHeightNormal,
        shape = if (isSmall) shapeSmall else shapeNormal,
        padding = padding
    )
}