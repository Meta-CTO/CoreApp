package com.metacto.core.utils.launchers

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIViewController
import platform.darwin.NSObject

object ShareLauncherHelper: NSObject() {
    fun shareText(text: String, fromViewController: UIViewController) {
        val activityViewController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        fromViewController.presentViewController(activityViewController, animated = true, completion = null)
    }
}