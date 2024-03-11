package com.metacto.core.utils.launchers

import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

class IntentLauncher : IIntentLauncher {
    override fun launchEmail(email: String, subject: String?, body: String?) {
        // Create the url
        val urlString = "mailto:$email?subject=${subject.orEmpty()}&body=${body.orEmpty()}"
        val url = NSURL.URLWithString(urlString) ?: return

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(url).not()) return

        // Then open it
        UIApplication.sharedApplication.openURL(url)
    }

    override fun launchShareText(text: String) {
        // Get and validate the root view controller
        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController
            ?: return

        // Create the activity controller
        val activityController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        // Then present it
        rootViewController.presentViewController(
            viewControllerToPresent = activityController,
            animated = true,
            completion = null
        )
    }

    override fun launchPhone(phone: String) {
        // Create the url
        val urlString = "tel://$phone"
        val url = NSURL.URLWithString(urlString) ?: return

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(url).not()) return

        // Then open it
        UIApplication.sharedApplication.openURL(url)
    }

    override fun launchBrowser(url: String) {
        // Create the url
        val nsUrl = NSURL.URLWithString(url) ?: return

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(nsUrl).not()) return

        // Then open it
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}