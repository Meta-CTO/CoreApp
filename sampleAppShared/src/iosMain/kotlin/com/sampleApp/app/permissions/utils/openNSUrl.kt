package com.sampleApp.app.permissions.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

fun openNSUrl(string: String) {
    val settingsUrl: NSURL = NSURL.URLWithString(string)!!
    if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
        UIApplication.sharedApplication.openURL(settingsUrl)
    } else throw Throwable("Cannot open URL: $string")
}

fun openAppSettingsPage() {
    openNSUrl(UIApplicationOpenSettingsURLString)
}
