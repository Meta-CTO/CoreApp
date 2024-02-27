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
actual fun randomUUID(): String = NSUUID().UUIDString()