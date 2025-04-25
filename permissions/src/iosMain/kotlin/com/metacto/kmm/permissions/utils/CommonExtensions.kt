package com.metacto.kmm.permissions.utils


import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

fun runOnMainThread(block: () -> Unit) {
    dispatch_async(dispatch_get_main_queue()) {
        block()
    }
}

fun runOnMainThreadCatching(block: () -> Unit) {
    try {
        runOnMainThread(block)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun openUrl(url: String) = runOnMainThreadCatching {
    val settingsUrl = NSURL.URLWithString(url)!!
    if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
        UIApplication.sharedApplication.openURL(
            url = settingsUrl,
            options = emptyMap<Any?, Any?>(),
            completionHandler = null
        )
    }
}

fun openAppSettings() {
    openUrl(UIApplicationOpenSettingsURLString)
}