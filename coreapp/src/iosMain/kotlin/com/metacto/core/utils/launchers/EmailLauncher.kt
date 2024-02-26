package com.metacto.core.utils.launchers

import androidx.compose.runtime.Composable
import io.ktor.http.encodeURLParameter
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual object EmailLauncher {
    actual fun launchEmail(email: String, subject: String?, body: String?, options: EmailLauncherOptions) {
        var urlString = "mailto:$email"

        if (subject != null) {
            urlString += "?subject=$subject"
            if (body != null) {
                urlString += "&body=$body"
            }
        } else if (body != null) {
            urlString += "?body=$body"
        }


        val url = NSURL.URLWithString(urlString.encodeURLParameter())

        if(url != null && UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
}

actual class EmailLauncherOptions

@Composable
actual fun rememberEmailLauncherOptions(): EmailLauncherOptions {
    return EmailLauncherOptions()
}