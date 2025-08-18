package com.metacto.core.ui.components.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ColorImage(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier.background(color)
    )
}