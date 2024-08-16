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
fun TertiaryStrokedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.tertiaryStrokedButton.textStyle,
    iconColor: Color? = CoreTheme.colors.tertiaryStrokedButton.iconColor,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.tertiaryStrokedBtnPaddingVertical,
        horizontal = CoreTheme.spacings.tertiaryStrokedBtnPaddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.tertiaryStrokedButton.textColor,
    backgroundColor: Color = CoreTheme.colors.tertiaryStrokedButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.tertiaryStrokedBtnMinHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.tertiaryStrokedBtnMinHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.tertiaryStrokedBtnShapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.tertiaryStrokedBtnShapeNormal,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.tertiaryStrokedBtnStrokeWidth,
        color = CoreTheme.colors.tertiaryStrokedButton.strokeColor
    ),
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