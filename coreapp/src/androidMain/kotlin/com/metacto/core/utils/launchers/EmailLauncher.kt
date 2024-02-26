package com.metacto.core.utils.launchers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual object EmailLauncher {
    actual fun launchEmail(
        email: String,
        subject: String?,
        body: String?,
        options: EmailLauncherOptions,
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            if (subject != null) putExtra(Intent.EXTRA_SUBJECT, subject)
            if (body != null) putExtra(Intent.EXTRA_TEXT, body)
        }
        if (intent.resolveActivity(options.context.packageManager) != null) {
            options.context.startActivity(intent)
        }
    }
}

actual data class EmailLauncherOptions(
    val context: Context
)

@Composable
actual fun rememberEmailLauncherOptions(): EmailLauncherOptions {
    val context = LocalContext.current
    return EmailLauncherOptions(context)
}