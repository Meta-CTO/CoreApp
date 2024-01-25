package com.metacto.core.utils

import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.posix.memcpy

class ResourceProvider(
    private val bundle: NSBundle,
    private val fileManager: NSFileManager
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

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getBytes(res: AssetResource): ByteArray? {
        return try {
            val contentsAtPath = fileManager.contentsAtPath(res.path) ?: return null
            val byteArray = ByteArray(contentsAtPath.length.toInt())
            byteArray.usePinned {
                memcpy(it.addressOf(0), contentsAtPath.bytes, contentsAtPath.length)
            }
            byteArray
        } catch (_: Throwable) {
            null
        }
    }
}