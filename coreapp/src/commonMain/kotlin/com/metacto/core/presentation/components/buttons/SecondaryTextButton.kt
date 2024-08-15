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
fun SecondaryTextButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isEnabled: Boolean = true,
    color: Color = CoreTheme.colors.secondaryTextButton.textColor,
    disabledColor: Color = color.copy(alpha = 0.3f),
    textStyle: TextStyle = CoreTheme.typography.secondaryTextBtnTextStyle,
    iconSize: Dp = CoreTheme.spacings.secondaryTextBtnIconSize,
    iconColor: Color? = CoreTheme.colors.secondaryTextButton.iconColor,
    spacing: Dp = CoreTheme.spacings.secondaryTextBtnSpacing,
    padding: PaddingValues = PaddingValues(vertical = CoreTheme.spacings.secondaryTextBtnPaddingVertical),
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