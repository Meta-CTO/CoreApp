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
fun SecondaryStrokedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.secondaryStrokedBtn.textStyle,
    iconColor: Color? = CoreTheme.colors.secondaryStrokedButton.iconColor,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    iconSize: Dp = CoreTheme.spacings.secondaryStrokedButton.iconSize,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.secondaryStrokedButton.paddingVertical,
        horizontal = CoreTheme.spacings.secondaryStrokedButton.paddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.secondaryStrokedButton.textColor,
    backgroundColor: Color = CoreTheme.colors.secondaryStrokedButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.secondaryStrokedButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.secondaryStrokedButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.secondaryStrokedButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.secondaryStrokedButton.shapeNormal,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.secondaryStrokedButton.strokeWidth,
        color = CoreTheme.colors.secondaryStrokedButton.strokeColor
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
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        backgroundColor = backgroundColor,
        minHeight = if (isSmall) minHeightSmall else minHeightNormal,
        shape = if (isSmall) shapeSmall else shapeNormal,
        border = border,
        padding = padding
    )
}