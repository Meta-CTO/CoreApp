package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.core.utils.extensions.isGesturesNavBarEnabled

@Composable
fun ScreenInsetsContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // Prepare nav bars modifier
    val navBarsModifier = if (isGesturesNavBarEnabled().not()) {
        Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    } else {
        Modifier
    }

    // Render outer box that will respect status and nav bars
    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .then(navBarsModifier)
    ) {
        // Render inner box that will respect ime padding
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            // Then render content
            content()
        }
    }
}
