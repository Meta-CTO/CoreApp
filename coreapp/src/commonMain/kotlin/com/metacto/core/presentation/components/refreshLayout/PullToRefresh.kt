package com.metacto.core.presentation.components.refreshLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PullToRefresh(
    modifier: Modifier,
    isRefreshable: Boolean,
    isRefreshing: Boolean,
    onRefresh: (() -> Unit)? = null,
    content: @Composable () -> Unit
)