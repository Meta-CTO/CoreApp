@file:OptIn(ExperimentalForeignApi::class)

package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSLocale
import platform.Foundation.NSThread
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue

inline fun <T1> mainContinuation(
    noinline block: (T1) -> Unit
): (T1) -> Unit = { arg1 ->
    if (NSThread.isMainThread()) {
        block.invoke(arg1)
    } else {
        Dispatchers.Main.run {
            block.invoke(arg1)
        }
    }
}

inline fun <T1, T2> mainContinuation(
    noinline block: (T1, T2) -> Unit
): (T1, T2) -> Unit = { arg1, arg2 ->
    if (NSThread.isMainThread()) {
        block.invoke(arg1, arg2)
    } else {
        Dispatchers.Main.run {
            block.invoke(arg1, arg2)
        }
    }
}

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

fun runOnIOThread(block: () -> Unit) {
    val ioQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0u)
    dispatch_async(ioQueue) {
        block()
    }
}

fun runOnIOThreadCatching(block: () -> Unit) {
    try {
        runOnIOThread(block)
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

actual fun getPlatformType(): PlatformType {
    return PlatformType.IOS
}

actual fun randomUUID(): String = NSUUID().UUIDString()

actual fun getSystemLanguage(): String {
    return NSLocale.currentLocale.languageCode ?: "en"
}