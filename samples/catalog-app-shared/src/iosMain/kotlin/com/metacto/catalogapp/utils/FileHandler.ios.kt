package com.metacto.catalogapp.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUserDomainMask
import platform.Foundation.writeToFile
///TODO THIS FOR DEMO ONLY  JUST TO CREATE FILES TO GET PATHS

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class FileHandler {
    @OptIn(ExperimentalForeignApi::class)
    actual fun createFolderAndFile(folderName: String, fileName: String, content: String): String {
        val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
        val documentsDirectory = paths.first() as String
        val folderPath = "$documentsDirectory/$folderName"

        val fileManager = NSFileManager.defaultManager

        if (!fileManager.fileExistsAtPath(folderPath)) {
            fileManager.createDirectoryAtPath(
                path = folderPath,
                withIntermediateDirectories = true,
                attributes = null,
                error = null
            )
        }

        val filePath = "$folderPath/$fileName"
        val nsString = content as NSString
        nsString.writeToFile(filePath, atomically = true)

        return filePath
    }
}
