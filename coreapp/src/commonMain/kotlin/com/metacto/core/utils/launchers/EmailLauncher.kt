package com.metacto.core.utils.launchers

import androidx.compose.runtime.Composable

expect object EmailLauncher {
    fun launchEmail(email: String, subject: String?, body: String?, options: EmailLauncherOptions)
}

expect class EmailLauncherOptions

@Composable
expect fun rememberEmailLauncherOptions(): EmailLauncherOptions