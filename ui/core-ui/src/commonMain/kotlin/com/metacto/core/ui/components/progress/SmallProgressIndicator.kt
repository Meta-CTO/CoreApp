package com.metacto.core.ui.components.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.metacto.core.ui.theme.CoreTheme

@Composable
fun SmallProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = CoreTheme.colors.smallProgressIndicator.progressColor,
    padding: Dp = CoreTheme.spacings.smallProgressIndicator.padding,
    progressSize: Dp = CoreTheme.spacings.progressSizeSmall
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(progressSize),
            color = color
        )
    }
}