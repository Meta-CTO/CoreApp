package com.metacto.core.utils.file

import com.metacto.core.utils.file.IFileManager
import dev.gitlive.firebase.remoteconfig.toByteArray
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile

class FileManager : IFileManager {

    override fun readFile(filePath: String): ByteArray {
        val nsData = NSData.dataWithContentsOfFile(filePath)
        requireNotNull(nsData) { "Can't access file at: $filePath" }

        return nsData.toByteArray()
    }
}