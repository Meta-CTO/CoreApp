package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PrimaryStrokedButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    iconColor: Color? = CoreTheme.colors.primary,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
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
        textColor = CoreTheme.colors.primary,
        iconColor = iconColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        backgroundColor = CoreTheme.colors.background,
        minHeight = if (isSmall) CoreTheme.spacings.btnMinHeightSmall else CoreTheme.spacings.btnMinHeightNormal,
        shape = if (isSmall) CoreTheme.shapes.xSmall else CoreTheme.shapes.small,
        border = BorderStroke(
            width = CoreTheme.spacings.stroke,
            color = CoreTheme.colors.primary
        ),
        padding = padding
    )
}