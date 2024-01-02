@file:OptIn(ExperimentalResourceApi::class)
package com.metacto.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import androidx.compose.ui.text.platform.Font as PlatformFont

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
                resource("font/$res.ttf").readBytes()
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