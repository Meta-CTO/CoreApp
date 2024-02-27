@file:OptIn(ExperimentalForeignApi::class)

package com.metacto.core.utils.extensions

import com.metacto.core.utils.PlatformType
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSThread
import platform.Foundation.NSUUID
import platform.Foundation.NSValue
import platform.UIKit.CGRectValue
import platform.UIKit.UIKeyboardFrameEndUserInfoKey
import platform.UIKit.UIKeyboardWillHideNotification
import platform.UIKit.UIKeyboardWillShowNotification

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

actual inline fun <T1> mainContinuation(
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

actual inline fun <T1, T2> mainContinuation(
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


actual fun randomUUID(): String = NSUUID().UUIDString()