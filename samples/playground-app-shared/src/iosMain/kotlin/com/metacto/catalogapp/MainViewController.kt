package com.metacto.catalogapp

import androidx.compose.ui.window.ComposeUIViewController
import com.metacto.catalogapp.presentation.app.app.AppContent

fun MainViewController() = ComposeUIViewController {
    AppContent()
}