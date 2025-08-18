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
fun PrimaryFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textColor: Color = CoreTheme.colors.primaryFilledButton.textColor,
    iconColor: Color? = CoreTheme.colors.primaryFilledButton.iconColor,
    backgroundColor: Color = CoreTheme.colors.primaryFilledButton.bgColor,
    textStyle: TextStyle = CoreTheme.typography.primaryFilledButton.textStyle,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    iconSize: Dp = CoreTheme.spacings.primaryFilledButton.iconSize,
    disabledBgColor: Color = CoreTheme.colors.primaryFilledButton.disabledBgColor,
    loadingColor: Color = CoreTheme.colors.primaryFilledButton.loadingColor,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    elevation: Dp = CoreTheme.spacings.primaryFilledButton.elevation,
    contentSpacing: Dp = CoreTheme.spacings.primaryFilledButton.contentSpacing,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.primaryFilledButton.paddingVertical,
        horizontal = CoreTheme.spacings.primaryFilledButton.paddingHorizontal
    ),
    minHeightSmall: Dp = CoreTheme.spacings.primaryFilledButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.primaryFilledButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.primaryFilledButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.primaryFilledButton.shapeNormal,
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
        backgroundColor = backgroundColor,
        startIconPainter = startIconPainter,
        elevation = elevation,
        contentSpacing = contentSpacing,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
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
