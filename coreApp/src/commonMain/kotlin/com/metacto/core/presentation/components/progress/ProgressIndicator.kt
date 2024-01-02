package com.metacto.core.presentation.components.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.noRippleClickable

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color,
    isBlocking: Boolean
) {
    // Prepare clickable modifier
    val clickableModifier = if (isBlocking) Modifier.noRippleClickable {} else Modifier

    // Then render the indicator
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .then(clickableModifier)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(CoreTheme.spacings.progressSizeNormal),
            strokeWidth = CoreTheme.spacings.progressStrokeNormal,
            color = color
        )
    }
}