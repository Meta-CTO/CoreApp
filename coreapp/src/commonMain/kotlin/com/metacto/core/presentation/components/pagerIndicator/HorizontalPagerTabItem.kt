package com.metacto.core.presentation.components.pagerIndicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun HorizontalPagerTabItem(
    modifier: Modifier = Modifier,
    title: String,
    bgColor: Color = CoreTheme.colors.primaryContainer,
    textColor: Color = CoreTheme.colors.onPrimaryContainer,
    inActiveBgColor: Color = CoreTheme.colors.background,
    inActiveTextColor: Color = CoreTheme.colors.tertiary,
    shape: Shape = CoreTheme.shapes.horizontalPagerTabItemShape,
    textStyle: TextStyle = CoreTheme.typography.bodySmall,
    textPadding: PaddingValues = PaddingValues(
        vertical = CoreTheme.spacings.horizontalPagerTabItemTextPaddingVertical,
        horizontal = CoreTheme.spacings.horizontalPagerTabItemTextPaddingHorizontal
    ),
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    // Prepare text color
    val bgColorState by animateColorAsState(
        targetValue = if (isSelected) bgColor else inActiveBgColor,
        label = "Tab BG Color State"
    )
    val textColorState by animateColorAsState(
        targetValue = if (isSelected) textColor else inActiveTextColor,
        label = "Tab Text Color State"
    )

    // Tab card
    Card(
        colors = CardDefaults.cardColors(
            containerColor = bgColorState
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = shape,
        modifier = modifier.noRippleClickable(onClick = onClick)
    ) {
        // Tab text
        Text(
            text = title,
            style = textStyle,
            textAlign = TextAlign.Center,
            color = textColorState,
            modifier = Modifier.padding(textPadding)
        )
    }
}