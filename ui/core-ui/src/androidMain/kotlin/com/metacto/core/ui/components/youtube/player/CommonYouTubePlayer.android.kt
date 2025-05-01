package com.metacto.core.ui.components.youtube.player

import com.metacto.core.ui.components.youtube.player.model.YouTubeExecCommand
import com.multiplatform.webview.web.WebViewNavigator

internal actual fun executeCommand(
    navigator: WebViewNavigator,
    execCommand: YouTubeExecCommand
) {
    navigator.evaluateJavaScript(execCommand.command())
}