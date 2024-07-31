package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun TransparentStrokedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.transparentStrokedBtn,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isLoading: Boolean = false,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.transparentStrokedBtnPaddingVertical,
        horizontal = CoreTheme.spacings.transparentStrokedBtnPaddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.transparentStrokedBtnTextColor,
    iconColor: Color = CoreTheme.colors.transparentStrokedBtnIconColor,
    backgroundColor: Color = CoreTheme.colors.transparentStrokedBtnBg,
    elevation: Dp = CoreTheme.spacings.transparentStrokedBtnElevation,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.transparentStrokedBtnStrokeWidth,
        color = CoreTheme.colors.transparentStrokedBtnStrokeColor
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
        contentAlignment = contentAlignment,
        elevation = elevation,
        border = border,
        padding = padding
    )
}