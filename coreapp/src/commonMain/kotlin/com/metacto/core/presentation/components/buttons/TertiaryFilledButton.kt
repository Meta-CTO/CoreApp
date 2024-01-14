package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun TertiaryFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    iconColor: Color? = CoreTheme.colors.onTertiary,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmallHeight: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.btnPaddingVertical,
        horizontal = CoreTheme.spacings.btnPaddingHorizontal
    ),
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = CoreTheme.colors.onTertiary,
        iconColor = iconColor,
        backgroundColor = CoreTheme.colors.tertiary,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        minHeight = if (isSmallHeight) CoreTheme.spacings.btnMinHeightSmall else CoreTheme.spacings.btnMinHeightNormal,
        shape = if (isSmallHeight) CoreTheme.shapes.xSmall else CoreTheme.shapes.small,
        padding = padding
    )
}
