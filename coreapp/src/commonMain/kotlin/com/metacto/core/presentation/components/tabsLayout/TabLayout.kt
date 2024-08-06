package com.metacto.core.presentation.components.tabsLayout

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    tabTitles: ImmutableList<TabItemModel>,
    currentPage: Int,
    showIndicator: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    tabItemHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = CoreTheme.spacings.tabTextPadding,
        alignment = Alignment.CenterHorizontally
    ),
    onTabClicked: ((Int) -> Unit)? = null
) {
    // Get main objects
    val scrollState = rememberScrollState()

    // Render tabs
    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
    ) {
        tabTitles.forEachIndexed { index, tab ->
            TabItem(
                modifier = Modifier.weight(1f),
                title = tab.title,
                activeIcon = tab.activeIcon,
                inActiveIcon = tab.inactiveIcon,
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