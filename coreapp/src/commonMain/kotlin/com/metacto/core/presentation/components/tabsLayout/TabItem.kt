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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.components.dividers.VerticalDivider
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.extensions.noRippleClickable
import com.metacto.core.utils.painterResource
import dev.icerock.moko.resources.ImageResource

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    title: String,
    activeBgColor: Color = CoreTheme.colors.tabActiveBgColor,
    inactiveBgColor: Color = CoreTheme.colors.tabInactiveBgColor,
    activeTextColor: Color = CoreTheme.colors.tabActiveTextColor,
    inactiveTextColor: Color = CoreTheme.colors.tabInactiveTextColor,
    activeIndicatorColor: Color = CoreTheme.colors.tabActiveIndicatorColor,
    inactiveIndicatorColor: Color = CoreTheme.colors.tabInactiveIndicatorColor,
    textStyle: TextStyle = CoreTheme.typography.tabText,
    showIndicator: Boolean = false,
    activeIcon: CommonImageResource? = null,
    inActiveIcon: CommonImageResource? = null,
    iconSize: Dp = CoreTheme.spacings.tabIconSize,
    isSelected: Boolean,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = CoreTheme.spacings.tabTextPadding,
        alignment = Alignment.CenterHorizontally
    ),
    onClick: () -> Unit
) {

    // Prepare bg color
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) activeBgColor else inactiveBgColor,
        label = "Tab BG Color State"
    )

    // Prepare text color
    val textColor by animateColorAsState(
        targetValue = if (isSelected) activeTextColor else inactiveTextColor,
        label = "Tab Text Color State"
    )

    // Prepare indicator color
    val indicatorColor by animateColorAsState(
        targetValue = if (isSelected) activeIndicatorColor else inactiveIndicatorColor,
        label = "Tab indicator Color State"
    )

    // Prepare the icon
    val tabIcon = if (activeIcon != null && inActiveIcon != null) {
        if (isSelected) activeIcon else inActiveIcon
    } else {
        null
    }

    // Tab indicator thickness
    val tabIndicatorThickness = if (isSelected) {
        CoreTheme.spacings.tabActiveIndicatorThickness
    } else {
        CoreTheme.spacings.tabInactiveIndicatorThickness
    }

    // Tab item
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .background(bgColor)
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement
        ) {

            // show the icon in case of available
            if (tabIcon != null) {
                Image(
                    painter = painterResource(tabIcon),
                    contentDescription = title,
                    modifier = Modifier.size(iconSize)
                )
            }

            // show the title
            SingleLineText(
                text = title,
                style = textStyle,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier.wrapContentSize()
            )
        }

        if (showIndicator) {
            VerticalDivider(
                color = indicatorColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabIndicatorThickness)
            )
        }
    }
}