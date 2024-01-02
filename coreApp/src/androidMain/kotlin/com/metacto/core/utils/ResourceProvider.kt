package com.metacto.core.utils

import android.annotation.SuppressLint
import android.content.Context
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format

class ResourceProvider(
    private val context: Context
) : IResourceProvider {

    override fun getString(res: StringResource, vararg args: Any): String {
        return res.format(args).toString(context)
    }

    @SuppressLint("DiscouragedApi")
    override fun getString(resName: String) = try {
        val resId = context.resources.getIdentifier(resName, "string", context.packageName)
        context.getString(resId)
    } catch (_: Throwable) {
        null
    }

    override fun getPluralString(
        res: PluralsResource,
        quantity: Int,
        vararg args: Any
    ): String {
        return res.format(quantity, args).toString(context)
    }
}