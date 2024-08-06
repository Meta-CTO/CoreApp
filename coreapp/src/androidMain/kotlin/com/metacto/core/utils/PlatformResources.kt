package com.metacto.core.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.ImageResource
import androidx.compose.ui.res.painterResource as androidPainterResource

actual typealias CommonImageResource = Int

@Composable
actual fun painterResource(imageResource: CommonImageResource): Painter {
    return androidPainterResource(imageResource)
}

actual fun ImageResource.asCommon(): CommonImageResource {
    return drawableResId
}

actual object PlatformResources {

    @SuppressLint("DiscouragedApi")
    @Composable
    actual fun font(
        res: String,
        weight: FontWeight,
        style: FontStyle
    ): Font? {
        val context = LocalContext.current
        val id = context.resources.getIdentifier(res, "font", context.packageName)

        return Font(
            resId = id,
            weight = weight,
            style = style
        )
    }
}