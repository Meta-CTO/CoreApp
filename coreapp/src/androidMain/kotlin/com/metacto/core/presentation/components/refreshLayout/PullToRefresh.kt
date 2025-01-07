package com.metacto.core.presentation.components.refreshLayout

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.pullRefreshIf
import com.metacto.core.utils.extensions.rememberPullRefreshStateIf

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun PullToRefresh(
    modifier: Modifier,
    isRefreshable: Boolean,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit)?,
    content: @Composable () -> Unit
) {

   val  refreshIndicatorColor = CoreTheme.colors.pullRefreshIndicator
    val refreshIndicatorBgColor = CoreTheme.colors.pullRefreshIndicatorBackground

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
        content

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