package com.metacto.sampleapp.presentation.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.components.toolbar.Toolbar
import com.metacto.core.ui.extensions.modifyIf
import com.metacto.sampleapp.presentation.theme.colors
import com.metacto.sampleapp.presentation.theme.spacings

@Composable
internal fun AppScreenColumn(
    modifier: Modifier = Modifier,
    bgColor: Color = colors.background,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    topPadding: Dp = spacings.spacing0,
    bottomPadding: Dp = spacings.spacing0,
    startPadding: Dp = spacings.spacing16,
    endPadding: Dp = spacings.spacing16,
    enableSafeInsets: Boolean = true,
    enableImeInsets: Boolean = false,
    isScrollable: Boolean = false,
    onScroll: (() -> Unit)? = null,
    isRefreshable: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    showToolbar: Boolean = false,
    showBack: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    title: String? = null,
    contentAlpha: Float = 1f,
    content: @Composable ColumnScope.() -> Unit,
) {
    // Render screen column
    ScreenColumn(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor)
            .alpha(contentAlpha)
            .modifyIf(enableImeInsets) {
                imePadding()
            }
            .modifyIf(enableSafeInsets) {
                statusBarsPadding().navigationBarsPadding()
            },
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        topPadding = topPadding,
        bottomPadding = bottomPadding,
        startPadding = startPadding,
        endPadding = endPadding,
        enableSafeInsets = false,
        isScrollable = isScrollable,
        onScroll = onScroll,
        isRefreshable = isRefreshable,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        mainContent = content,
        toolbar = {
            if (showToolbar) {
                Toolbar(
                    title = title,
                    modifier = Modifier.fillMaxWidth(),
                    showStartIcon = showBack,
                    onStartIconClick = {
                        onBackClick?.invoke()
                    }
                )
            }
        }
    )
}