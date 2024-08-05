package com.metacto.core.presentation.components.tabsLayout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayoutView(
    modifier: Modifier = Modifier,
    tabTitles: List<TabItemModel>,
    pagerState: PagerState,
    showIndicator: Boolean = false,
    tabsHorizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    tabHorizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(
        space = CoreTheme.spacings.tabTextPadding,
        alignment = Alignment.CenterHorizontally
    ),
    onTabClicked: ((Int) -> Unit)? = null
) {
    // Get main objects
    val scrollState = rememberScrollState()
    val coroutinesScope = rememberCoroutineScope()

    // Render tabs
    Row(
        horizontalArrangement = tabsHorizontalArrangement,
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
                horizontalArrangement = tabHorizontalArrangement,
                isSelected = index == pagerState.currentPage,
                onClick = {
                    // Change pager page
                    coroutinesScope.launch { pagerState.animateScrollToPage(index) }

                    // Notify the listener
                    onTabClicked?.invoke(index)
                }
            )
        }
    }
}