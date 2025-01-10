package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.isKeyboardVisible
import org.koin.compose.koinInject

@Composable
fun ScreenColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    topPadding: Dp = CoreTheme.spacings.screenPadding,
    bottomPadding: Dp = CoreTheme.spacings.screenPadding,
    startPadding: Dp = CoreTheme.spacings.screenPadding,
    endPadding: Dp = CoreTheme.spacings.screenPadding,
    enableSafeInsets: Boolean = true,
    isScrollable: Boolean = false,
    onScroll: (() -> Unit)? = null,
    onScrollUp: (() -> Unit)? = null,
    onScrollDown: (() -> Unit)? = null,
    isRefreshable: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    refreshIndicatorColor: Color = CoreTheme.colors.pullRefreshIndicator,
    refreshIndicatorBgColor: Color = CoreTheme.colors.pullRefreshIndicatorBackground,
    toolbar: @Composable () -> Unit = {},
    topContent: (@Composable () -> Unit)? = null,
    mainContent: @Composable ColumnScope.() -> Unit,
) {
    // Prepare main objects
    val globalState = koinInject<ICoreGlobalState>()
    val isKeyboardVisible by isKeyboardVisible()

    // Container column
    SafeInsetsColumn(
        modifier = modifier.fillMaxSize(),
        enableSafeInsets = enableSafeInsets
    ) {
        // Render toolbar
        toolbar()

        // Then render content column
        CoreColumn(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            isScrollable = isScrollable,
            onScroll = onScroll,
            onScrollUp = onScrollUp,
            onScrollDown = onScrollDown,
            isRefreshable = isRefreshable,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            refreshIndicatorColor = refreshIndicatorColor,
            refreshIndicatorBgColor = refreshIndicatorBgColor,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = startPadding)
                .padding(end = endPadding)
        ) {
            // Render top content if required
            topContent?.invoke()

            // Top padding
            Spacer(
                modifier = Modifier.height(topPadding)
            )

            // Render content
            mainContent(this)

            // Bottom padding
            Spacer(
                modifier = Modifier.height(bottomPadding)
            )
        }
    }
}
