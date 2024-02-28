package com.metacto.core.utils.launchers

import io.ktor.http.encodeURLParameter
import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

actual class IntentLauncher: IIntentLauncher {
    actual override fun launchEmail(email: String, subject: String?, body: String?) {
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

    actual override fun launchShareText(text: String) {
        val application = UIApplication.sharedApplication
        val rootViewController = application.keyWindow?.rootViewController

        if(rootViewController != null) {
            shareText(text = text, fromViewController = rootViewController)
        }
    }

    private fun shareText(text: String, fromViewController: UIViewController) {
        val activityViewController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        fromViewController.presentViewController(activityViewController, animated = true, completion = null)
    }
}