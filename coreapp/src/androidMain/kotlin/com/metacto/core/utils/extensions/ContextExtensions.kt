package com.metacto.core.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity


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

inline fun <reified Activity : ComponentActivity> Context.getActivity(): Activity? {
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

fun Context.getAppIconResId(): Int? {
    return try {
        packageManager.getApplicationInfo(packageName, 0).icon
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

fun Context.getLauncherPendingIntent(
    extras: Map<String, Any> = emptyMap()
): PendingIntent? {
    val intent = packageManager.getLaunchIntentForPackage(packageName)
    intent?.addExtras(extras)
    return PendingIntent.getActivity(
        this,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
}

fun Context.openAppSettings() {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", applicationContext.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}