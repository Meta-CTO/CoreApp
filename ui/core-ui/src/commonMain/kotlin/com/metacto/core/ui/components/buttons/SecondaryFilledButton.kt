package com.metacto.core.ui.components.buttons

import androidx.compose.foundation.layout.PaddingValues
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
fun SecondaryFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.secondaryFilledButton.textStyle,
    iconColor: Color? = CoreTheme.colors.secondaryFilledButton.iconColor,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    disabledBgColor: Color = CoreTheme.colors.secondaryFilledButton.disabledBgColor,
    iconSize: Dp = CoreTheme.spacings.secondaryFilledButton.iconSize,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    elevation: Dp = CoreTheme.spacings.secondaryFilledButton.elevation,
    contentSpacing: Dp = CoreTheme.spacings.secondaryFilledButton.contentSpacing,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.secondaryFilledButton.paddingVertical,
        horizontal = CoreTheme.spacings.secondaryFilledButton.paddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.secondaryFilledButton.textColor,
    loadingColor: Color = CoreTheme.colors.secondaryFilledButton.loadingColor,
    backgroundColor: Color = CoreTheme.colors.secondaryFilledButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.secondaryFilledButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.secondaryFilledButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.secondaryFilledButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.secondaryFilledButton.shapeNormal,
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
        elevation = elevation,
        contentSpacing = contentSpacing,
        endIconVector = endIconVector,
        disabledBackgroundColor = disabledBgColor,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        loadingColor = loadingColor,
        onClick = onClick,
        minHeight = if (isSmall) minHeightSmall else minHeightNormal,
        shape = if (isSmall) shapeSmall else shapeNormal,
        padding = padding
    )
}
