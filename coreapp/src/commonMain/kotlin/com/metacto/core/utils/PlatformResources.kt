package com.metacto.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

expect object PlatformResources {
    @Composable
    fun font(
        res: String,
        weight: FontWeight,
        style: FontStyle = FontStyle.Normal
    ): Font?
}