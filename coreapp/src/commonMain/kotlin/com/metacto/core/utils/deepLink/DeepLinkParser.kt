package com.metacto.core.utils.deepLink

interface DeepLinkParser {
    fun parse(url: String): DeepLink?
}