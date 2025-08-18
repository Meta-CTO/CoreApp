package com.metacto.catalogapp

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import com.metacto.catalogapp.crash.CrashLogger
import com.metacto.catalogapp.presentation.app.app.AppContent
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.staticCFunction
import org.koin.compose.koinInject
import platform.Foundation.NSSetUncaughtExceptionHandler
import platform.Foundation.NSUncaughtExceptionHandler
import kotlin.experimental.ExperimentalNativeApi

private var crashLogger: CrashLogger? = null

@OptIn(ExperimentalNativeApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.DoNothing
    },
    content = {
        // Handle crashes
        crashLogger = koinInject<CrashLogger>()
        handleNSUncaughtException()

        // Render app content
        AppContent()

        // Clear crash reporter when the view is disposed
        DisposableEffect(Unit) {
            onDispose {
                crashLogger = null
            }
        }
    }
)

@OptIn(ExperimentalForeignApi::class)
private fun handleNSUncaughtException() {
    val handler: CPointer<NSUncaughtExceptionHandler> = staticCFunction { nsException ->
        val cause = Throwable(nsException?.reason)
        crashLogger?.logException(
            Throwable(message = nsException?.name, cause = cause)
        )
    }
    NSSetUncaughtExceptionHandler(handler)
}