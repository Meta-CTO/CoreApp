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
fun TertiaryFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.tertiaryFilledButton.textStyle,
    iconColor: Color? = CoreTheme.colors.tertiaryFilledButton.iconColor,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.tertiaryFilledBtnPaddingVertical,
        horizontal = CoreTheme.spacings.tertiaryFilledBtnPaddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.tertiaryFilledButton.textColor,
    backgroundColor: Color = CoreTheme.colors.tertiaryFilledButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.tertiaryFilledBtnMinHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.tertiaryFilledBtnMinHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.tertiaryFilledBtnShapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.tertiaryFilledBtnShapeNormal,
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
        minHeight = if (isSmall) minHeightSmall else minHeightNormal,
        shape = if (isSmall) shapeSmall else shapeNormal,
        padding = padding
    )
}
