package com.metacto.core.utils.deepLink

val Class = IDeepLinkRegistry::class

interface IDeepLinkRegistry {
    fun emitDeepLink(link: String)
    fun emitDynamicLink(link: String)
    suspend fun observeDeepLinks(callback: (String) -> Unit)
}