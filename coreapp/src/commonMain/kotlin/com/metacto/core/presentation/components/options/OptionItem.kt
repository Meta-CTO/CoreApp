package com.metacto.core.presentation.components.options

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.options.models.OptionUIModel
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.toColor
import com.metacto.core.utils.painterResource

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    option: OptionUIModel,
    onClick: () -> Unit,
    padding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.optionItemPaddingVertical,
        horizontal = CoreTheme.spacings.optionItemPaddingHorizontal
    ),
    iconSize: Dp = CoreTheme.spacings.optionItemPaddingIconSize,
    textSpacing: Dp = CoreTheme.spacings.optionItemPaddingTextSpacing,
    arrowSize: Dp = CoreTheme.spacings.optionItemPaddingArrowSize,
    defaultColor: Color = CoreTheme.colors.optionItem.defaultColor,
    optionColor: Color = CoreTheme.colors.optionsArrow,
    textStyle: TextStyle = CoreTheme.typography.optionItemTextStyle
) {
    // Prepare the color
    val color = option.color.toColor() ?: defaultColor

    // Container row
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(padding)
    ) {
        // Render icon if possible
        if (option.icon != null) {
            Image(
                painter = painterResource(option.icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier.size(iconSize)
            )
        }

        // Text
        Text(
            text = option.title,
            color = color,
            style = textStyle,
            modifier = Modifier
                .weight(1f)
                .padding(start = textSpacing)
        )

        // Render arrow if required
        if (option.hasArrow) {
            Image(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                colorFilter = ColorFilter.tint(optionColor),
                modifier = Modifier.size(arrowSize)
            )
        }
    }
}