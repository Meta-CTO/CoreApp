package com.metacto.core.utils.deepLink

internal expect object DynamicLinkProcessor {
    fun process(
        url: String,
        onSuccess: (String) -> Unit
    )
}