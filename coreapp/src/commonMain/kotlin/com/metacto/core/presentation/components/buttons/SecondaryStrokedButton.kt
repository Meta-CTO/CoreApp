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
    textStyle: TextStyle = CoreTheme.typography.secondaryStrokedBtnTextStyle,
    iconColor: Color? = CoreTheme.colors.secondaryStrokedBtnIconColor,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.secondaryStrokedBtnPaddingVertical,
        horizontal = CoreTheme.spacings.secondaryStrokedBtnPaddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.secondaryStrokedBtnTextColor,
    backgroundColor: Color = CoreTheme.colors.secondaryStrokedBtnBg,
    minHeightSmall: Dp = CoreTheme.spacings.secondaryStrokedBtnMinHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.secondaryStrokedBtnMinHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.secondaryStrokedBtnShapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.secondaryStrokedBtnShapeNormal,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.secondaryStrokedBtnStrokeWidth,
        color = CoreTheme.colors.secondaryStrokedBtnStrokeColor
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