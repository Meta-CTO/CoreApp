package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun SocialButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.socialButton.textStyle,
    iconPainter: Painter? = null,
    iconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    isSmall: Boolean = false,
    disabledBgColor: Color = CoreTheme.colors.socialButton.disabledBgColor,
    elevation: Dp = CoreTheme.spacings.socialButton.elevation,
    contentSpacing: Dp = CoreTheme.spacings.socialButton.contentSpacing,
    iconSize: Dp = CoreTheme.spacings.socialButton.iconSize,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.socialButton.paddingVertical,
        horizontal = CoreTheme.spacings.socialButton.paddingHorizontal
    ),
    textColor: Color = CoreTheme.colors.socialButton.textColor,
    loadingColor: Color = CoreTheme.colors.socialButton.loadingColor,
    backgroundColor: Color = CoreTheme.colors.socialButton.bgColor,
    contentAlignment: Alignment.Horizontal = Alignment.Start,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.socialButton.shapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.socialButton.shapeNormal,
    border: BorderStroke = BorderStroke(
        width = CoreTheme.spacings.socialButton.strokeWidth,
        color = CoreTheme.colors.socialButton.strokeColor
    ),
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textColor = textColor,
        textStyle = textStyle,
        startIconPainter = iconPainter,
        startIconVector = iconVector,
        iconSize = iconSize,
        disabledBackgroundColor = disabledBgColor,
        elevation = elevation,
        contentSpacing = contentSpacing,
        backgroundColor = backgroundColor,
        isEnabled = isEnabled,
        isLoading = isLoading,
        loadingColor = loadingColor,
        onClick = onClick,
        iconColor = null,
        contentAlignment = contentAlignment,
        border = border,
        shape = if (isSmall) shapeSmall else shapeNormal,
        padding = padding
    )
}