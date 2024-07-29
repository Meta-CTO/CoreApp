package com.metacto.core.presentation.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.metacto.core.presentation.theme.CoreTheme

@Composable
fun PrimaryCheckableButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    textStyle: TextStyle = CoreTheme.typography.btnLabelMedium,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.btnCheckablePaddingVertical,
        horizontal = CoreTheme.spacings.btnCheckablePaddingHorizontal
    ),
    isChecked: Boolean = false,
    shape: RoundedCornerShape = CoreTheme.shapes.primaryCheckableBtnShape,
    onClick: () -> Unit = {},
    checkedBackgroundColor: Color = CoreTheme.colors.checkedBtnBg,
    uncheckedBackgroundColor: Color = CoreTheme.colors.uncheckedBtnBg,
    checkedTextColor: Color = CoreTheme.colors.checkedBtnTextColor,
    uncheckedTextColor: Color = CoreTheme.colors.uncheckedBtnTextColor
) {
    val bgColor by animateColorAsState(
        if (isChecked) checkedBackgroundColor else uncheckedBackgroundColor
    )
    val textColor by animateColorAsState(
        if (isChecked) checkedTextColor else uncheckedTextColor
    )

    // Render text
    Text(
        text = text.orEmpty(),
        color = textColor,
        style = textStyle,
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(padding)
    )
}
