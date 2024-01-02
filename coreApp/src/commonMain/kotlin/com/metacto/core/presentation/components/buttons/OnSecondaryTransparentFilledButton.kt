package com.metacto.core.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
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
        vertical = CoreTheme.spacings.btnPaddingVertical,
        horizontal = CoreTheme.spacings.btnPaddingHorizontal
    ),
    onClick: () -> Unit = {}
) {
    val bgColor = CoreTheme.colors.onSecondary.copy(alpha = 0.1f)

    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = CoreTheme.colors.onSecondary.copy(alpha = 0.9f),
        iconColor = CoreTheme.colors.onSecondary,
        elevation = CoreTheme.spacings.noSpacing,
        backgroundColor = bgColor,
        disabledBackgroundColor = bgColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        isLoading = isLoading,
        onClick = onClick,
        minHeight = CoreTheme.spacings.btnMinHeightSmall,
        shape = CoreTheme.shapes.small,
        padding = padding
    )
}