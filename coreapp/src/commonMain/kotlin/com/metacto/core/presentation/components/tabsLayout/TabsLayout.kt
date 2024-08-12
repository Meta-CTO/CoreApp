package com.metacto.core.presentation.components.tabsLayout

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.collections.immutable.ImmutableList

const val TABS_SCROLL_MIN_COUNT = 3

@Composable
fun TabsLayout(
    modifier: Modifier = Modifier,
    tabModels: ImmutableList<TabItemModel>,
    currentPage: Int,
    showIndicator: Boolean = false,
    tabsScrollMinCount: Int = TABS_SCROLL_MIN_COUNT,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    tabItemHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = CoreTheme.spacings.tabTextPadding,
        alignment = Alignment.CenterHorizontally
    ),
    onTabClicked: ((Int) -> Unit)? = null
) {
    val tabsCounts = tabModels.size
    val isRequiredScroll = tabsCounts > tabsScrollMinCount

    // init the tabLayout modifier
    val tabsLayoutModifier = if (isRequiredScroll) {
        modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
    } else {
        modifier.fillMaxWidth()
    }

    // Container row
    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = tabsLayoutModifier
    ) {
        // init the tab item modifier
        val tabItemModifier = if (isRequiredScroll) {
            modifier
                .fillMaxHeight()
                .defaultMinSize(minWidth = CoreTheme.spacings.tabItemMinWidth)
        } else {
            Modifier.weight(1f)
        }

        tabModels.forEachIndexed { index, tab ->
            TabItem(
                modifier = tabItemModifier,
                title = tab.title,
                activeIcon = tab.activeIcon,
                inActiveIcon = tab.inactiveIcon,
                activeRemoteIcon = tab.activeRemoteIcon,
                inActiveRemoteIcon = tab.inactiveRemoteIcon,
                showIndicator = showIndicator,
                horizontalArrangement = tabItemHorizontalArrangement,
                isSelected = index == currentPage,
                onClick = {
                    // Notify the listener
                    onTabClicked?.invoke(index)
                }
            )
        }
    }
}