package com.sampleApp.app.presentation.main.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.base.BaseTabScreen
import com.sampleApp.app.presentation.home.HomeTab
import com.sampleApp.app.presentation.main.MainContract.Event
import com.sampleApp.app.presentation.main.MainContract.State
import com.sampleApp.app.presentation.main.components.navBar.NavigationBar
import com.sampleApp.app.presentation.profile.ProfileTab
import com.sampleApp.app.presentation.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MainContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Prepare pager state
    val pagerState = rememberPagerState { 2 }

    // Scroll to selected tab
    LaunchedEffect(state.currentTab) {
        // Scroll to this page
        pagerState.scrollToPage(state.currentTab)

        // And notify tab that it's displayed
        val displayedTab = state.currentTab.toTab()
        displayedTab?.onDisplayed()
    }

    // Container column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        // Content pager
        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = pagerState.pageCount,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            // Get current tab and render content
            val currentTab = index.toTab()
            currentTab?.Content()
        }

        // Bottom nav bar
        NavigationBar(
            selectedTab = state.currentTab,
            onItemClick = { index ->
                onEvent(Event.ChangeTab(index))
            },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        )
    }
}

private fun Int.toTab(): BaseTabScreen<*>? {
    return when (this) {
        0 -> HomeTab
        1 -> ProfileTab
        else -> null
    }
}