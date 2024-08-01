package com.metacto.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.ImageResource

expect class CommonImageResource

@Composable
expect fun painterResource(imageResource: CommonImageResource): Painter

expect fun ImageResource.asCommon(): CommonImageResource

expect object PlatformResources {
    @Composable
    fun font(
        res: String,
        weight: FontWeight,
        style: FontStyle = FontStyle.Normal
    ): Font?
}