package com.metacto.core.files

interface IFileManager {
    @Throws(Throwable::class)
    fun readFile(filePath: String): ByteArray

    fun clearFolder(folderPath: String): Boolean

    fun deleteFile(filePath: String): Boolean
}