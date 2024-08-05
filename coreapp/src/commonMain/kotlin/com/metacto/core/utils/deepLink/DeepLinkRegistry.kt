package com.metacto.core.utils.deepLink

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

internal object DeepLinkRegistry : IDeepLinkRegistry {
    private val deepLinksFlow = MutableSharedFlow<String>(replay = 1)

    override fun emitDynamicLink(link: String) {
        DynamicLinkProcessor.process(link) {
            deepLinksFlow.tryEmit(it)
        }
    }

    override fun emitDeepLink(link: String) {
        deepLinksFlow.tryEmit(link)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeDeepLinks(callback: (String) -> Unit) {
        deepLinksFlow.collectLatest {
            callback.invoke(it)
            deepLinksFlow.resetReplayCache()
        }
    }
}