package com.metacto.core.utils.deepLink

val Class = IDeepLinkManager::class

interface IDeepLinkManager {
    fun emitDeepLink(link: String)
    fun emitDynamicLink(link: String)
    suspend fun observeDeepLinks(callback: (DeepLink) -> Unit)
}