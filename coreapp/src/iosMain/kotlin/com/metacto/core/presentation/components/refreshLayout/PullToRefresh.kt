package com.metacto.core.presentation.components.refreshLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
actual fun PullToRefresh(
    modifier: Modifier,
    isRefreshable: Boolean,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit)?,
    content: @Composable () -> Unit
) {
   val  refreshContent: @Composable RefreshLayoutState.() -> Unit = remember {
        { PullToRefreshContent() }
    }

    RefreshLayout(
        refreshContent = refreshContent,
        refreshLayoutState = createState(),
        modifier = modifier,
        content = content
    )
}

@Composable
private fun createState() = rememberRefreshLayoutState {
    coroutineScope.launch {
        delay(2000)
        setRefreshState(RefreshContentStateEnum.Stop)
    }
}