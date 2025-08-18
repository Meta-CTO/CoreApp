package com.metacto.core.deepLink

val Class = IDeepLinkManager::class

interface IDeepLinkManager {
    fun emitDeepLink(link: String)
    suspend fun observeDeepLinks(callback: (DeepLink) -> Unit)
}