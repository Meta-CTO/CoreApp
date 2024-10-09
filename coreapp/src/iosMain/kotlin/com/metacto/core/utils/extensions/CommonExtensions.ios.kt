@file:OptIn(ExperimentalForeignApi::class)

package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSThread
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.NSValue
import platform.UIKit.CGRectValue
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.UIKeyboardFrameEndUserInfoKey
import platform.UIKit.UIKeyboardWillHideNotification
import platform.UIKit.UIKeyboardWillShowNotification
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue

actual fun getPlatformType(): PlatformType {
    return PlatformType.IOS
}

fun observeKeyboardHeight(
    onKeyboardVisible: (Float) -> Unit,
    onKeyboardHidden: () -> Unit
) {
    // Observe keyboard visibility
    NSNotificationCenter.defaultCenter()
        .addObserverForName(UIKeyboardWillShowNotification, null, null) {
            val keyboardFrameBegin = it?.userInfo?.getValue(UIKeyboardFrameEndUserInfoKey)
            (keyboardFrameBegin as? NSValue)?.CGRectValue?.useContents {
                onKeyboardVisible.invoke(size.height.toFloat())
            }
        }

    // Observe keyboard hidden
    NSNotificationCenter.defaultCenter()
        .addObserverForName(UIKeyboardWillHideNotification, null, null) {
            onKeyboardHidden.invoke()
        }
}

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

fun openNSUrl(string: String) {
    val settingsUrl: NSURL = NSURL.URLWithString(string)!!
    if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
        UIApplication.sharedApplication.openURL(settingsUrl)
    } else throw Throwable("Cannot open URL: $string")
}

fun openAppSettingsPage() {
    openNSUrl(UIApplicationOpenSettingsURLString)
}

fun runOnMainThread(block: () -> Unit) {
    dispatch_async(dispatch_get_main_queue()) {
        block()
    }
}

fun runOnIOThread(block: () -> Unit) {
    val ioQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0u)
    dispatch_async(ioQueue) {
        block()
    }
}

actual fun randomUUID(): String = NSUUID().UUIDString()