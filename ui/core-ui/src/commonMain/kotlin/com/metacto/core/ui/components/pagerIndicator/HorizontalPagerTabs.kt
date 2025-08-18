package com.metacto.core.ui.components.pagerIndicator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerTabs(
    modifier: Modifier = Modifier,
    tabTitles: List<String>,
    pagerState: PagerState,
    onTabClicked: ((Int) -> Unit)? = null,
    horizontalSpacing: Dp = CoreTheme.spacings.horizontalPagerTabs.horizontalSpacing
) {
    // Get main objects
    val scrollState = rememberScrollState()
    val coroutinesScope = rememberCoroutineScope()

    // Render tabs
    Row(
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        modifier = modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
    ) {
        tabTitles.forEachIndexed { index, title ->
            HorizontalPagerTabItem(
                title = title,
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