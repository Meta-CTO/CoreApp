package com.metacto.core.utils

import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource

interface IResourceProvider {
    fun getString(res: StringResource, vararg args: Any): String

    fun getString(resName: String): String?

    fun getPluralString(
        res: PluralsResource,
        quantity: Int,
        vararg args: Any
    ): String
}