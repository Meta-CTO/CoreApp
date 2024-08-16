package com.metacto.core.presentation.components.tabsLayout

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import com.metacto.core.presentation.components.images.AppImage
import com.metacto.core.presentation.components.texts.SingleLineText
import com.metacto.core.presentation.models.ImageUIModel
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    title: String,
    activeBgColor: Color = CoreTheme.colors.tapItem.activeBgColor,
    inactiveBgColor: Color = CoreTheme.colors.tapItem.inactiveBgColor,
    activeTextColor: Color = CoreTheme.colors.tapItem.activeTextColor,
    inactiveTextColor: Color = CoreTheme.colors.tapItem.inactiveTextColor,
    activeIndicatorColor: Color = CoreTheme.colors.tapItem.activeIndicatorColor,
    inactiveIndicatorColor: Color = CoreTheme.colors.tapItem.inactiveIndicatorColor,
    textStyle: TextStyle = CoreTheme.typography.tabItem.textStyle,
    showIndicator: Boolean = false,
    activeIcon: ImageUIModel? = null,
    inActiveIcon: ImageUIModel? = null,
    iconSize: Dp = CoreTheme.spacings.tabItem.iconSize,
    isSelected: Boolean,
    activeIndicatorThickness: Dp = CoreTheme.spacings.tabItem.activeIndicatorThickness,
    inActiveIndicatorThickness: Dp = CoreTheme.spacings.tabItem.inactiveIndicatorThickness,
    itemMinWidth: Dp = CoreTheme.spacings.tabItem.itemMinWidth,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = CoreTheme.spacings.tabItem.textPadding,
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
        activeIndicatorThickness
    } else {
        inActiveIndicatorThickness
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            // show the icon in case of available
            if (tabIcon != null) {
                AppImage(
                    image = tabIcon,
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
                    .defaultMinSize(minWidth = itemMinWidth)
                    .height(tabIndicatorThickness)
            )
        }
    }
}