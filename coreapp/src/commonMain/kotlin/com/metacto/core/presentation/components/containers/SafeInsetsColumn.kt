package com.metacto.core.presentation.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
fun SafeInsetsColumn(
    modifier: Modifier = Modifier,
    enableSafeInsets: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    // Prepare status bars modifier
    val statusBarsModifier = if (enableSafeInsets) {
        Modifier.windowInsetsPadding(WindowInsets.statusBars)
    } else {
        Modifier
    }

    // Prepare nav bars modifier
    val navBarsModifier = if (enableSafeInsets && isGesturesNavBarEnabled().not()) {
        Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    } else {
        Modifier
    }

    // Prepare safe drawing modifier
    val safeDrawingModifier = if (enableSafeInsets) {
        Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    } else {
        Modifier
    }

    // Render outer box that will respect status and nav bars
    Box(
        modifier = modifier
            .then(statusBarsModifier)
            .then(navBarsModifier)
    ) {
        // Render inner column that will respect ime padding
        Column(
            modifier = Modifier
                .then(safeDrawingModifier)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            // Then render content
            content(this)
        }
    }
}
