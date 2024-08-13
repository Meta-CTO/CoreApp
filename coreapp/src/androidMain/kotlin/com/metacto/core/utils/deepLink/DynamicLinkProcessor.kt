package com.metacto.core.utils.deepLink

import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

internal actual object DynamicLinkProcessor {
    actual fun process(
        url: String,
        onSuccess: (String) -> Unit
    ) {
        Firebase.dynamicLinks
            .getDynamicLink(Uri.parse(url))
            .addOnSuccessListener {
                val deepLink = it?.link?.toString()
                if (deepLink != null) {
                    onSuccess.invoke(it.toString())
                }
            }.addOnFailureListener {
                println("DynamicLinkProcessor:onFailure $it")
            }
    }
}