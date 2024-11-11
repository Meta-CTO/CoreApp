package com.metacto.core.utils.resources

import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource

interface IResourceProvider {
    fun getString(res: StringResource, vararg args: Any): String

    fun getString(resName: String): String?

    fun getPluralString(
        res: PluralStringResource,
        quantity: Int,
        vararg args: Any
    ): String

    suspend fun getBytes(res: String): ByteArray?
}