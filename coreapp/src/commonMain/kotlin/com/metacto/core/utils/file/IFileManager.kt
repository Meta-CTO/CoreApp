package com.metacto.core.utils.file

interface IFileManager {
    @Throws(Throwable::class)
    fun readFile(filePath: String): ByteArray

    fun clearFolder(folderPath: String): Boolean
}