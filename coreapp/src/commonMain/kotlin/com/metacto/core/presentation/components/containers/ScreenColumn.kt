package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.metacto.core.presentation.globalState.ICoreGlobalState
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.isKeyboardVisible
import com.metacto.core.utils.extensions.onScrolling
import org.koin.compose.rememberKoinInject

@Composable
fun ScreenColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    topPadding: Dp = CoreTheme.spacings.screenPadding,
    bottomPadding: Dp = CoreTheme.spacings.screenPadding,
    startPadding: Dp = CoreTheme.spacings.screenPadding,
    endPadding: Dp = CoreTheme.spacings.screenPadding,
    isScrollable: Boolean = false,
    onScrolled: (() -> Unit)? = null,
    toolbar: @Composable () -> Unit = {},
    topContent: (@Composable () -> Unit)? = null,
    mainContent: @Composable ColumnScope.() -> Unit,
) {
    // Prepare main objects
    val globalState = rememberKoinInject<ICoreGlobalState>()
    val isKeyboardVisible by isKeyboardVisible()
    val scrollState = rememberScrollState()

    // Prepare scroll modifier
    val scrollableModifier = if (isScrollable) {
        Modifier.verticalScroll(scrollState)
    } else {
        Modifier
    }

    // Listen for scroll events
    scrollState.onScrolling {
        // Invoke onScrolled callback
        onScrolled?.invoke()

        // TODO: When tap on input field the view is getting scrolled and keyboard is dismissed instatly
        // We need to fix tat issue first then enable dismiss event
//        // Dismiss keyboard if it's visible
//        if (isKeyboardVisible) {
//            globalState.dismissKeyboard()
//        }
    }

    // Container column
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Render toolbar
        toolbar()

        // Then render content column
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = startPadding)
                .padding(end = endPadding)
                .then(scrollableModifier)
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
