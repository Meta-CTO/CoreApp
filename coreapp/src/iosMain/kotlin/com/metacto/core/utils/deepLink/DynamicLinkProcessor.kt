package com.metacto.core.utils.deepLink

import cocoapods.FirebaseDynamicLinks.FIRDynamicLinks
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL

internal actual object DynamicLinkProcessor {
    @OptIn(ExperimentalForeignApi::class)
    actual fun process(
        url: String,
        onSuccess: (String) -> Unit
    ) {
        FIRDynamicLinks.dynamicLinks()
            .handleUniversalLink(NSURL(string = url)) { dynamicLink, error ->
                if (error != null) {
                    println("DynamicLinkProcessor:onFailure $error")
                } else {
                    val deepLink = dynamicLink?.url?.absoluteString
                    if (deepLink != null) {
                        onSuccess.invoke(deepLink)
                    }
                }
            }
    }
}