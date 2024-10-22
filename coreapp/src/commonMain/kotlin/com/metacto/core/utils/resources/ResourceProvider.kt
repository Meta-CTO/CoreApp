package com.metacto.core.utils.resources

import com.metacto.coreApp.resources.Res
import com.metacto.coreApp.resources.allStringResources
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getPluralString as getComposePluralString
import org.jetbrains.compose.resources.getString as getComposeString

@OptIn(ExperimentalResourceApi::class)
object ResourceProvider : IResourceProvider {

    override fun getString(res: StringResource, vararg args: Any): String {
        return runBlocking {
            getComposeString(res, *args)
        }
    }

    override fun getString(resName: String): String? {
        return runBlocking {
            Res.allStringResources[resName]?.let {
                getString(it)
            }
        }
    }

    override fun getPluralString(
        res: PluralStringResource,
        quantity: Int,
        vararg args: Any
    ): String {
        return runBlocking {
            getComposePluralString(res, quantity, args)
        }
    }

    override suspend fun getBytes(res: String) = try {
        Res.readBytes(res)
    } catch (_: Throwable) {
        null
    }
}