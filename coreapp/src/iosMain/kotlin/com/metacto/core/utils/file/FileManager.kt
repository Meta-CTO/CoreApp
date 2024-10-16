package com.metacto.core.utils.file

import dev.gitlive.firebase.remoteconfig.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.stringByAppendingPathComponent

class FileManager : IFileManager {

    override fun readFile(filePath: String): ByteArray {
        val nsData = NSData.dataWithContentsOfFile(filePath)
        requireNotNull(nsData) { "Can't access file at: $filePath" }

        return nsData.toByteArray()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun clearFolder(folderPath: String): Boolean {
        val fileManager = NSFileManager.defaultManager

        return try {
            // Get the list of files in the directory
            val files = fileManager.contentsOfDirectoryAtPath(folderPath, null)
            files?.forEach { file ->
                val filePath = (folderPath as NSString).stringByAppendingPathComponent(file as String)
                fileManager.removeItemAtPath(filePath, null)
            }
            true
        } catch (e: Exception) {
            println("Could not clear folder: $e")
            false
        }
    }
}