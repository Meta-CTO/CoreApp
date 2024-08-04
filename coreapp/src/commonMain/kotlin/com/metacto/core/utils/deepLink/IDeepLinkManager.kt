package com.metacto.core.utils.deepLink

val Class = IDeepLinkManager::class

interface IDeepLinkManager {
    fun handleDeepLink(link: String)
    fun handleDynamicLink(link: String)
    suspend fun onReceiveLink(callback: (String) -> Unit)
}