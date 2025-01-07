package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.refreshLayout.PullToRefresh
import com.metacto.core.utils.extensions.onScrolling
import com.metacto.core.utils.extensions.rememberScrollStateIf
import com.metacto.core.utils.extensions.verticalScrollIf

@Composable
fun CoreColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    isScrollable: Boolean = false,
    scrollState: ScrollState? = rememberScrollStateIf(isScrollable),
    onScrolled: (() -> Unit)? = null,
    isRefreshable: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    // Config scroll state
    scrollState?.let {
        it.onScrolling {
            onScrolled?.invoke()
        }
    }
    // Render content column
    val contentColumn = Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content,
            modifier = Modifier
                .fillMaxSize()
                .verticalScrollIf(isScrollable, scrollState)
        )

    PullToRefresh(
        isRefreshable = isRefreshable,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        content = { contentColumn },
        modifier  = modifier
    )
}