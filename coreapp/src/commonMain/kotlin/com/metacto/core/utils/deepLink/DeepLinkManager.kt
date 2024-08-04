package com.metacto.core.utils.deepLink

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

internal object DeepLinkManager : IDeepLinkManager {
    private val buffer = MutableSharedFlow<String>(replay = 1)

    override fun handleDynamicLink(link: String) {
        DynamicLinkProcessor.process(link) {
            buffer.tryEmit(it)
        }
    }

    override fun handleDeepLink(link: String) {
        buffer.tryEmit(link)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun onReceiveLink(callback: (String) -> Unit) {
        buffer.collectLatest {
            callback.invoke(it)
            buffer.resetReplayCache()
        }
    }
}