package com.metacto.core.presentation.components.buttons

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
fun FloatingButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    textColor: Color = CoreTheme.colors.floatingBtnTextColor,
    iconColor: Color = CoreTheme.colors.floatingBtnIconColor,
    iconSize: Dp = CoreTheme.spacings.iconSmall,
    backgroundColor: Color = CoreTheme.colors.onSecondary,
    minHeight: Dp = CoreTheme.spacings.btnMinHeightSmall,
    contentSpacing: Dp = CoreTheme.spacings.paddingSmall,
    shape: RoundedCornerShape = CoreTheme.shapes.xxxLarge,
    elevation: Dp = CoreTheme.spacings.floatingBtnElevation,
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
        minHeight = minHeight,
        contentSpacing = contentSpacing,
        shape = shape,
        elevation = elevation
    )
}