package com.metacto.core.deepLink

interface DeepLinkParser {
    fun parse(url: String): DeepLink?
}