package com.metacto.core.presentation.components.youtubePlayer

import com.metacto.core.presentation.components.youtubePlayer.model.YouTubeExecCommand
import com.multiplatform.webview.web.WebViewNavigator

internal actual fun executeCommand(
    navigator: WebViewNavigator,
    execCommand: YouTubeExecCommand
) {
    navigator.evaluateJavaScript(execCommand.command())
}