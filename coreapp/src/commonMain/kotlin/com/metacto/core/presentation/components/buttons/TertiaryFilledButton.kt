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
    iconSize: Dp = CoreTheme.spacings.socialButton.iconSize,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    disabledBgColor: Color = CoreTheme.colors.tertiaryFilledButton.disabledBgColor,
    elevation: Dp = CoreTheme.spacings.tertiaryFilledButton.elevation,
    contentSpacing: Dp = CoreTheme.spacings.tertiaryFilledButton.contentSpacing,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.tertiaryFilledButton.paddingVertical,
        horizontal = CoreTheme.spacings.tertiaryFilledButton.paddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.tertiaryFilledButton.textColor,
    backgroundColor: Color = CoreTheme.colors.tertiaryFilledButton.bgColor,
    minHeightSmall: Dp = CoreTheme.spacings.tertiaryFilledButton.minHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.tertiaryFilledButton.minHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.tertiaryFilledButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.tertiaryFilledButton.shapeNormal,
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
        disabledBackgroundColor = disabledBgColor,
        elevation = elevation,
        contentSpacing = contentSpacing,
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
