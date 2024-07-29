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
fun PrimaryFilledButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textColor: Color = CoreTheme.colors.primaryBtnTextColor,
    iconColor: Color? = CoreTheme.colors.primaryBtnIconColor,
    backgroundColor: Color = CoreTheme.colors.primaryBtnBg,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmall: Boolean = false,
    isLoading: Boolean = false,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.primaryFilledBtnPaddingVertical,
        horizontal = CoreTheme.spacings.primaryFilledBtnPaddingHorizontal
    ),
    minHeightSmall: Dp = CoreTheme.spacings.primaryFilledBtnMinHeightSmall,
    minHeightNormal: Dp = CoreTheme.spacings.primaryFilledBtnMinHeightNormal,
    shapeSmall: RoundedCornerShape = CoreTheme.shapes.primaryFilledBtnShapeSmall,
    shapeNormal: RoundedCornerShape = CoreTheme.shapes.primaryFilledBtnShapeNormal,
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconColor = iconColor,
        backgroundColor = backgroundColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
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
