package com.metacto.core.presentation.components.youtubePlayer

import com.metacto.core.presentation.components.youtubePlayer.model.YouTubeExecCommand
import com.metacto.core.utils.extensions.format
import com.multiplatform.webview.web.WebViewNavigator

private const val COMMAND_EXECUTOR_PATTERN = "javascript:%s"

internal actual fun executeCommand(
    navigator: WebViewNavigator,
    execCommand: YouTubeExecCommand
) {
    navigator.loadUrl(COMMAND_EXECUTOR_PATTERN.format(execCommand.command()))
}