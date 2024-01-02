package com.metacto.core.utils

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import platform.Foundation.NSBundle

class ResourceProvider(
    private val bundle: NSBundle
) : IResourceProvider {

    override fun getString(res: StringResource, vararg args: Any): String {
        return res.format(args).localized()
    }

    override fun getString(resName: String) = try {
        val string = bundle.localizedStringForKey(resName, null, null)
        if (string == resName) {
            bundle.localizedStringForKey(resName, null, null)
        } else string
    } catch (_: Throwable) {
        null
    }

    override fun getPluralString(
        res: PluralsResource,
        quantity: Int,
        vararg args: Any
    ): String {
        return res.format(quantity, args).localized()
    }
}