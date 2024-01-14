package com.metacto.core.utils.extensions

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import io.michaelrocks.libphonenumber.kotlin.metadata.defaultMetadataLoader

@SuppressLint("ComposableNaming")
@Composable
actual fun openUrlInBrowser(url: String) {
    LocalContext.current.openUrl(url)
}

@Composable
actual fun getNotchHeight() = 0.dp

@Composable
actual fun getBottomHandleHeight() = 0.dp

@Composable
actual fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap? {
    return remember(bytes) {
        if (bytes != null) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
        } else {
            null
        }
    }
}

@Composable
actual fun isGesturesNavBarEnabled(): Boolean {
    return LocalContext.current.getNavigationBarInteractionMode() == NAVIGATION_BAR_INTERACTION_MODE_GESTURE
}

@Composable
actual fun rememberPhoneNumberUtil(): PhoneNumberUtil {
    val context = LocalContext.current
    return remember {
        PhoneNumberUtil.Companion.createInstance(
            metadataLoader = defaultMetadataLoader(context)
        )
    }
}

@SuppressLint("ComposableNaming")
@Composable
actual fun setStatusBarColor(isDark: Boolean) {
    val insetsController = getInsetsController()
    LaunchedEffect(isDark) {
        insetsController?.let {
            it.isAppearanceLightStatusBars = isDark.not()
            it.isAppearanceLightNavigationBars = isDark.not()
        }
    }
}


@Composable
fun getInsetsController(): WindowInsetsControllerCompat? {
    val view = LocalView.current
    val window = LocalContext.current.getActivity()?.window

    return window?.let {
        WindowCompat.getInsetsController(window, view)
    }
}