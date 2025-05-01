package com.metacto.core.ui.components.loadMore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun LoadingMoreProgress(
    modifier: Modifier = Modifier,
    progressSize: Dp = CoreTheme.spacings.loadingMore.progressSize,
    strokeWidth: Dp = CoreTheme.spacings.loadingMore.strokeWidth,
    color: Color = CoreTheme.colors.loadingMore.progressColor,
) {
    // Container box
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            strokeWidth = strokeWidth,
            color = color,
            modifier = Modifier.size(progressSize)
        )
    }
}