package com.metacto.core.deepLink

import com.metacto.core.extensions.matchWithWildcard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.logger.Logger

internal class DeepLinkManager(
    private val appLogger: Logger,
    private val parsers: Map<String, DeepLinkParser>
) : IDeepLinkManager {
    private val deepLinksFlow = MutableSharedFlow<DeepLink>(replay = 1)

    override fun emitDeepLink(link: String) {
        validAndParseDeepLink(link)
    }

    private fun validAndParseDeepLink(url: String) {
        try {
            // Match the path with wildcards in parsers
            val deepLinkParser = parsers.keys
                .firstOrNull { url.matchWithWildcard(it) }
                ?.let { parsers[it] }
                ?: return

            // Parse the deep link
            val deepLink = deepLinkParser.parse(url)

            // Emit it if possible
            if (deepLink != null) {
                deepLinksFlow.tryEmit(deepLink)
            }
        } catch (e: Exception) {
            appLogger.error("Failed to parse deep link: $url - Error: ${e.message}")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeDeepLinks(callback: (DeepLink) -> Unit) {
        deepLinksFlow.collectLatest {
            callback.invoke(it)
            deepLinksFlow.resetReplayCache()
        }
    }
}