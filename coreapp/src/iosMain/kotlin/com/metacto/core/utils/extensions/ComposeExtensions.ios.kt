@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)

package com.metacto.core.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UINavigationBar

@Composable
actual fun openUrlInBrowser(url: String) {
    try {
        UIApplication.sharedApplication.openURL(NSURL(string = url.getValidUrl()))
    } catch (_: Throwable) {
    }
}

@Composable
actual fun getNotchHeight(): Dp {
    val window = UIApplication.sharedApplication.keyWindow
    val topPadding = window?.safeAreaInsets?.useContents { top }.orZero()
    val hasNotch = topPadding > 20
    val updatedPadding = if (hasNotch) topPadding.times(0.9) else topPadding

    return updatedPadding.dp
}

@Composable
actual fun getBottomHandleHeight(): Dp {
    val window = UIApplication.sharedApplication.keyWindow
    val bottomPadding = window?.safeAreaInsets?.useContents { bottom }.orZero()

    return bottomPadding.times(0.5).dp
}

@Composable
actual fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap? {
    return remember(bytes) {
        if (bytes != null) {
            Bitmap.makeFromImage(
                Image.makeFromEncoded(bytes)
            ).asComposeImageBitmap()
        } else {
            null
        }
    }
}

@Composable
actual fun isGesturesNavBarEnabled(): Boolean {
    return true
}

@Composable
actual fun setStatusBarColor(isDark: Boolean) {
    LaunchedEffect(isDark) {
        val color = if (isDark) UIColor.whiteColor() else UIColor.blackColor()
        UINavigationBar.appearance().titleTextAttributes = mapOf(
            "NSForegroundColorAttributeName" to color
        )
    }
}

@Composable
actual fun setNavigationBarColor(isDark: Boolean) {
    // No need to do that, iOS do this automatically
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun dismissKeyboard() {
    LocalSoftwareKeyboardController.current?.hide()
}