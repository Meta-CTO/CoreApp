package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.onScroll
import com.metacto.core.utils.extensions.pullRefreshIf
import com.metacto.core.utils.extensions.rememberPullRefreshStateIf
import com.metacto.core.utils.extensions.rememberScrollStateIf
import com.metacto.core.utils.extensions.verticalScrollIf

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoreColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    isScrollable: Boolean = false,
    scrollState: ScrollState? = rememberScrollStateIf(isScrollable),
    onScroll: (() -> Unit)? = null,
    onScrollUp: (() -> Unit)? = null,
    onScrollDown: (() -> Unit)? = null,
    isRefreshable: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    refreshIndicatorColor: Color = CoreTheme.colors.pullRefreshIndicator,
    refreshIndicatorBgColor: Color = CoreTheme.colors.pullRefreshIndicatorBackground,
    content: @Composable ColumnScope.() -> Unit
) {
    // Config scroll state
    scrollState?.onScroll(
        onScrollUp = onScrollUp,
        onScrollDown = onScrollDown,
        onScroll = onScroll
    )

    // Prepare refresh state
    val pullRefreshState = rememberPullRefreshStateIf(
        condition = isRefreshable,
        refreshing = isRefreshing,
        onRefresh = {
            onRefresh?.invoke()
        }
    )

    // Container box
    Box(
        modifier = modifier.pullRefreshIf(
            condition = isRefreshable,
            state = pullRefreshState
        )
    ) {
        // Render content column
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content,
            modifier = Modifier
                .fillMaxSize()
                .verticalScrollIf(isScrollable, scrollState)
        )

        // Render pull refresh indicator
        if (pullRefreshState != null) {
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = isRefreshing,
                state = pullRefreshState,
                contentColor = refreshIndicatorColor,
                backgroundColor = refreshIndicatorBgColor
            )
        }
    }
}