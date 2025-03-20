package com.metacto.core.presentation.components.youtubePlayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.components.youtubePlayer.handler.YouTubeActionHandler
import com.metacto.core.presentation.components.youtubePlayer.model.YouTubeEvent
import com.metacto.core.presentation.components.youtubePlayer.model.YouTubeExecCommand
import com.metacto.core.presentation.components.youtubePlayer.provider.ConstantHTMLContentProvider
import com.metacto.core.presentation.components.youtubePlayer.provider.HTMLContentProvider
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData

private const val BASE_URL = "https://www.youtube.com"
private const val BASE_MIME_TYPE = "text/html"
private const val BASE_ENCODING = "utf-8"
private const val PLAYER_VARS_KEY = "<<injectedPlayerVars>>"

private val htmlContentProvider: HTMLContentProvider = ConstantHTMLContentProvider()

/**
 * YouTube player composable player.
 *
 * @param modifier modifier for styling component
 * @param options player options builder. See [YouTubePlayerOptionsBuilder]
 * documentation for more details.
 * @param hostState host state to manage and execute commands. See [YouTubePlayerHostState]
 * @param actionListener listener for YouTube events. See [YouTubeEvent] documentation.
 * */
@Composable
internal fun CommonYouTubePlayer(
    modifier: Modifier = Modifier,
    options: YouTubePlayerOptionsBuilder = SimpleYouTubePlayerOptionsBuilder(),
    hostState: YouTubePlayerHostState,
    actionListener: ((YouTubeEvent) -> Unit)? = null,
) {
    val htmlContent: String = remember(options) {
        htmlContentProvider.provideHTMLContent()
            .replace(PLAYER_VARS_KEY, options.build())
    }
    val webViewState = rememberWebViewStateWithHTMLData(
        data = htmlContent,
        baseUrl = BASE_URL,
        mimeType = BASE_MIME_TYPE,
        encoding = BASE_ENCODING,
    )


    val navigator = rememberWebViewNavigator()
    val command: YouTubeExecCommand? = hostState.command
    webViewState.webSettings.apply {
        isJavaScriptEnabled = true
        androidWebSettings.apply {
            isAlgorithmicDarkeningAllowed = false
            safeBrowsingEnabled = false
            domStorageEnabled = true
            supportZoom = false
        }
    }

    LaunchedEffect(command) {
        command?.also { command ->
            executeCommand(navigator, command)
            hostState.complete()
        }
    }

    YouTubeActionHandler.handleAction(webViewState.pageTitle)?.also { event ->
        hostState.updateState(event)
        actionListener?.invoke(event)
    }

    WebView(
        modifier = modifier.fillMaxSize(),
        state = webViewState,
        navigator = navigator,
    )
}

internal expect fun executeCommand(
    navigator: WebViewNavigator,
    execCommand: YouTubeExecCommand,
)
