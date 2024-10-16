package com.metacto.core.utils.file

import java.io.File

class FileManager : IFileManager {

    override fun readFile(filePath: String): ByteArray {
        return File(filePath).readBytes()
    }

    override fun clearFolder(folderPath: String): Boolean {
        return File(folderPath).deleteRecursively()
    }
}