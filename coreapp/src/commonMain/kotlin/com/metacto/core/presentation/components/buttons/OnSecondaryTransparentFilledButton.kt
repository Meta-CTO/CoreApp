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
fun OnSecondaryTransparentFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelSmall,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.onSecondaryTransparentFilledBtnPaddingVertical,
        horizontal = CoreTheme.spacings.onSecondaryTransparentFilledBtnPaddingHorizontal
    ),
    backgroundColor: Color = CoreTheme.colors.onSecondaryTransparentFilledBtnBg,
    textColor: Color = CoreTheme.colors.onSecondaryTransparentFilledBtnTextColor,
    iconColor: Color = CoreTheme.colors.onSecondaryTransparentFilledBtnIconColor,
    elevation: Dp = CoreTheme.spacings.onSecondaryTransparentFilledBtnElevation,
    minHeight: Dp = CoreTheme.spacings.onSecondaryTransparentFilledBtnMinHeight,
    shape: RoundedCornerShape = CoreTheme.shapes.onSecondaryTransparentFilledBtnShape,
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        elevation = elevation,
        backgroundColor = backgroundColor,
        disabledBackgroundColor = backgroundColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        minHeight = minHeight,
        shape = shape,
        padding = padding
    )
}