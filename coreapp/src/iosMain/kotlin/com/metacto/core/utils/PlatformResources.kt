@file:OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
package com.metacto.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.ImageResource
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes
import androidx.compose.ui.text.platform.Font as PlatformFont
import dev.icerock.moko.resources.compose.painterResource as mokoPainterResource

actual typealias CommonImageResource = ImageResource

@Composable
actual fun painterResource(imageResource: CommonImageResource): Painter {
    return mokoPainterResource(imageResource)
}

actual fun ImageResource.asCommon(): CommonImageResource {
    return this
}

actual object PlatformResources {
    private val fontsCache: MutableMap<String, Font> = mutableMapOf()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    actual fun font(
        res: String,
        weight: FontWeight,
        style: FontStyle
    ): Font? {
        return fontsCache.getOrPut(res) {
            val byteArray = runBlocking {
                readResourceBytes("font/$res.ttf")
            }

            PlatformFont(
                identity = res,
                data = byteArray,
                weight = weight,
                style = style
            )
        }
    }
}