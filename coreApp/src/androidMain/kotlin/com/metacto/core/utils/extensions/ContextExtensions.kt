package com.metacto.core.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        else -> {
            var context = this
            while (context is ContextWrapper) {
                context = context.baseContext
                if (context is Activity) return context
            }
            null
        }
    }
}

fun Context.openUrl(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (_: Throwable) {
        Toast.makeText(this, "No browser installed", Toast.LENGTH_LONG).show()
    }
}


const val NAVIGATION_BAR_INTERACTION_MODE_THREE_BUTTON = 0
const val NAVIGATION_BAR_INTERACTION_MODE_TWO_BUTTON = 1
const val NAVIGATION_BAR_INTERACTION_MODE_GESTURE = 2

@SuppressLint("DiscouragedApi")
fun Context.getNavigationBarInteractionMode(): Int {
    val resourceId = resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
    return if (resourceId > 0) resources.getInteger(resourceId) else NAVIGATION_BAR_INTERACTION_MODE_THREE_BUTTON
}

