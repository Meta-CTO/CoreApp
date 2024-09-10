package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
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
fun PrimaryStrokedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.primaryStrokedButton.textStyle,
    textColor: Color = CoreTheme.colors.primaryStrokedButton.textColor,
    iconColor: Color? = CoreTheme.colors.primaryStrokedButton.iconColor,
    disabledBgColor: Color = CoreTheme.colors.primaryStrokedButton.disabledBgColor,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    iconSize: Dp = CoreTheme.spacings.primaryStrokedButton.iconSize,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    elevation: Dp = CoreTheme.spacings.primaryStrokedButton.elevation,
    contentSpacing: Dp = CoreTheme.spacings.primaryStrokedButton.contentSpacing,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.primaryStrokedButton.paddingVertical,
        horizontal = CoreTheme.spacings.primaryStrokedButton.paddingHorizontal
    ),
    backgroundColor: Color = CoreTheme.colors.primaryStrokedButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.primaryStrokedButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.primaryStrokedButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.primaryStrokedButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.primaryStrokedButton.shapeNormal,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.primaryStrokedButton.strokeWidth,
        color = CoreTheme.colors.primaryStrokedButton.strokeColor
    ),
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        iconSize = iconSize,
        disabledBackgroundColor = disabledBgColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        contentSpacing = contentSpacing,
        elevation = elevation,
        onClick = onClick,
        backgroundColor = backgroundColor,
        minHeight = if (isSmall) minHeightSmall else minHeightNormal,
        shape = if (isSmall) shapeSmall else shapeNormal,
        border = border,
        padding = padding
    )
}