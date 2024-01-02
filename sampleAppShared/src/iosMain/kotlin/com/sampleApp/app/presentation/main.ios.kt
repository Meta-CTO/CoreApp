package com.sampleApp.app.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.ComposeUIViewController
import com.metacto.core.utils.extensions.observeKeyboardHeight
import com.sampleApp.app.presentation.app.app.AppContent
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    // Prepare keyboard height state
    val keyboardHeight = mutableStateOf(0f)

    // Listen for keyboard height
    observeKeyboardHeight(
        onKeyboardVisible = {
            keyboardHeight.value = it
        },
        onKeyboardHidden = {
            keyboardHeight.value = 0f
        }
    )

    return ComposeUIViewController {
        // Then render app
        AppContent(
            iOSKeyboardHeight = keyboardHeight.value
        )
    }
}