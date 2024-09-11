package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun TransparentStrokedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.transparentStrokedButton.textStyle,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    iconSize: Dp = CoreTheme.spacings.transparentStrokedButton.iconSize,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.transparentStrokedButton.paddingVertical,
        horizontal = CoreTheme.spacings.transparentStrokedButton.paddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.transparentStrokedButton.textColor,
    iconColor: Color = CoreTheme.colors.transparentStrokedButton.iconColor,
    backgroundColor: Color = CoreTheme.colors.transparentStrokedButton.bgColor,
    elevation: Dp = CoreTheme.spacings.transparentStrokedButton.elevation,
    contentSpacing: Dp = CoreTheme.spacings.transparentStrokedButton.contentSpacing,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.transparentStrokedButton.strokeWidth,
        color = CoreTheme.colors.transparentStrokedButton.strokeColor
    ),
    isSmall: Boolean = false,
    minHeightSmall: Dp = CoreTheme.spacings.transparentStrokedButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.transparentStrokedButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.transparentStrokedButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.transparentStrokedButton.shapeNormal,
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        iconSize = iconSize,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        contentSpacing = contentSpacing,
        backgroundColor = backgroundColor,
        contentAlignment = contentAlignment,
        elevation = elevation,
        border = border,
        padding = padding,
        minHeight = if (isSmall) minHeightSmall else minHeightNormal,
        shape = if (isSmall) shapeSmall else shapeNormal,
    )
}