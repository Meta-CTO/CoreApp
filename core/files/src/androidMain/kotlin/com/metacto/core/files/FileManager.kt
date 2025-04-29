package com.metacto.core.files

import java.io.File

class FileManager : IFileManager {

    override fun readFile(filePath: String): ByteArray {
        return File(filePath).readBytes()
    }

    override fun clearFolder(folderPath: String): Boolean {
        return File(folderPath).deleteRecursively()
    }

    override fun deleteFile(filePath: String): Boolean {
        return File(filePath).delete()
    }
}