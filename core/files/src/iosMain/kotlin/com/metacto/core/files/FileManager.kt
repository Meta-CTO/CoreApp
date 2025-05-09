package com.metacto.core.files

import com.metacto.core.extensions.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUserDomainMask
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.stringByAppendingPathComponent
import platform.Foundation.writeToFile

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
                val filePath =
                    (folderPath as NSString).stringByAppendingPathComponent(file as String)
                fileManager.removeItemAtPath(filePath, null)
            }
            true
        } catch (e: Throwable) {
            println("Could not clear folder at $folderPath, Error: $e")
            false
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun deleteFile(filePath: String): Boolean {
        return try {
            // Attempt to remove the file at the given path
            NSFileManager.defaultManager.removeItemAtPath(filePath, null)
            true
        } catch (e: Throwable) {
            println("Could not delete file at: $filePath, Error: $e")
            false
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun createFile(dirName: String?, fileName: String, content: String): String {
        try {
            val fileManager = NSFileManager.defaultManager
            val documentsDirectory = NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true
            ).first() as String

            val filePath = if (dirName.isNullOrEmpty().not()) {
                val folderPath = "$documentsDirectory/$dirName"

                // Create directory if it doesn't exist
                if (!fileManager.fileExistsAtPath(folderPath)) {
                    fileManager.createDirectoryAtPath(
                        path = folderPath,
                        withIntermediateDirectories = true,
                        attributes = null,
                        error = null
                    )
                }

                "$folderPath/$fileName"
            } else {
                "$documentsDirectory/$fileName"
            }

            // Create or overwrite the file with content
            (content as NSString).writeToFile(filePath, atomically = true)

            return filePath
        } catch (e: Throwable) {
            println("Error creating file: $e")
            return ""
        }

    }
}