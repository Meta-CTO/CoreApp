package com.sampleApp.app.presentation

import androidx.compose.ui.window.ComposeUIViewController
import com.sampleApp.app.presentation.app.app.AppContent

fun MainViewController() = ComposeUIViewController {
    // Then render app
    AppContent()
}