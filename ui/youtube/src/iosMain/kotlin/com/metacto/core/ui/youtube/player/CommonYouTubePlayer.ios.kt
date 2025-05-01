package com.metacto.core.ui.youtube.player

import com.metacto.core.extensions.format
import com.metacto.core.ui.youtube.player.model.YouTubeExecCommand
import com.multiplatform.webview.web.WebViewNavigator

private const val COMMAND_EXECUTOR_PATTERN = "javascript:%s"

internal actual fun executeCommand(
    navigator: WebViewNavigator,
    execCommand: YouTubeExecCommand
) {
    navigator.loadUrl(COMMAND_EXECUTOR_PATTERN.format(execCommand.command()))
}