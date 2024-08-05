package com.metacto.core.presentation.components.tabsLayout

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.components.dividers.VerticalDivider
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    title: String,
    bgColor: Color = CoreTheme.colors.primaryContainer,
    textColor: Color = CoreTheme.colors.onPrimaryContainer,
    inActiveBgColor: Color = CoreTheme.colors.background,
    inActiveTextColor: Color = CoreTheme.colors.tertiary,
    indicatorColor: Color = CoreTheme.colors.onPrimaryContainer,
    inActiveIndicatorColor: Color = CoreTheme.colors.tertiary,
    textStyle: TextStyle = CoreTheme.typography.tabText,
    showIndicator: Boolean = false,
    activeIcon: ImageResource? = null,
    inActiveIcon: ImageResource? = null,
    iconSize: Dp = CoreTheme.spacings.tabIconSize,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = CoreTheme.spacings.tabTextPadding,
        alignment = Alignment.CenterHorizontally
    ),
    isSelected: Boolean,
    onClick: () -> Unit
) {

    // Prepare bg color
    val bgColorState by animateColorAsState(
        targetValue = if (isSelected) bgColor else inActiveBgColor,
        label = "Tab BG Color State"
    )

    // Prepare text color
    val textColorState by animateColorAsState(
        targetValue = if (isSelected) textColor else inActiveTextColor,
        label = "Tab Text Color State"
    )

    // Prepare indicator color
    val indicatorColorState by animateColorAsState(
        targetValue = if (isSelected) indicatorColor else inActiveIndicatorColor,
        label = "Tab indicator Color State"
    )

    // prepare the icon
    val tabIconState = if (activeIcon != null && inActiveIcon != null) {
        if (isSelected) activeIcon else inActiveIcon
    } else {
        null
    }

    val tabIndicatorThickness =
        if (isSelected) CoreTheme.spacings.tabActiveIndicatorThickness
        else CoreTheme.spacings.tabInactiveIndicatorThickness

    // Tab card

    Column(
        modifier = modifier.noRippleClickable(onClick = onClick).background(bgColorState),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement
        ) {

            // show the icon in case of available
            if (tabIconState != null) {
                Image(
                    painter = painterResource(tabIconState),
                    contentDescription = title,
                    modifier = Modifier.size(iconSize)
                )
            }

            // show the title
            Text(
                text = title,
                style = textStyle,
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = textColorState,
                modifier = Modifier.wrapContentSize()
            )
        }

        if (showIndicator) {
            VerticalDivider(
                color = indicatorColorState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabIndicatorThickness)
            )
        }
    }
}