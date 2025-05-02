package com.metacto.sampleapp

import androidx.compose.ui.window.ComposeUIViewController
import com.metacto.sampleapp.presentation.app.app.AppContent

fun MainViewController() = ComposeUIViewController {
    AppContent()
}