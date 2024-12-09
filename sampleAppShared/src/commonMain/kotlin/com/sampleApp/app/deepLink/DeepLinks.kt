package com.sampleApp.app.deepLink

import com.metacto.core.utils.deepLink.DeepLink
import com.metacto.core.utils.deepLink.DeepLinkParser
import io.ktor.http.Url

internal val DEEP_LINK_PARSERS = mapOf(
    "/set-password" to SetPasswordDeepLinkParser,
    "/archives" to ArchivesLinkParser,
    "archives" to ArchivesLinkParser,
)

internal data class SetPasswordDeepLink(val passwordToken: String) : DeepLink()

internal data object ArchivesDeepLink : DeepLink()

internal object SetPasswordDeepLinkParser : DeepLinkParser {
    override fun parse(url: String): DeepLink? {
        // Get and validate password token
        val parsedUrl = Url(url)
        val passwordToken = parsedUrl.parameters["passwordToken"] ?: return null

        // Then return the SetPassword deep link
        return SetPasswordDeepLink(passwordToken)
    }
}

internal object ArchivesLinkParser : DeepLinkParser {
    override fun parse(url: String): DeepLink? {
        return ArchivesDeepLink
    }
}