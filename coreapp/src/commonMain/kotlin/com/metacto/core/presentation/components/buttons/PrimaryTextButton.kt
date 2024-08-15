package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PrimaryTextButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isEnabled: Boolean = true,
    color: Color = CoreTheme.colors.primaryTextButton.textColor,
    disabledColor: Color = color.copy(alpha = 0.3f),
    textStyle: TextStyle = CoreTheme.typography.primaryTextBtnTextStyle,
    iconSize: Dp = CoreTheme.spacings.primaryTextBtnIconSize,
    iconColor: Color? = CoreTheme.colors.primaryTextButton.iconColor,
    spacing: Dp = CoreTheme.spacings.primaryTextBtnSpacing,
    padding: PaddingValues = PaddingValues(vertical = CoreTheme.spacings.primaryTextBtnPaddingVertical),
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    BaseTextButton(
        modifier = modifier,
        text = text,
        isEnabled = isEnabled,
        color = color,
        disabledColor = disabledColor,
        textStyle = textStyle,
        iconSize = iconSize,
        iconColor = iconColor,
        spacing = spacing,
        padding = padding,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        onClick = onClick
    )
}