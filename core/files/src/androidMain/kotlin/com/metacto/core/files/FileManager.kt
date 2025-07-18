package com.metacto.core.files

import android.content.Context
import java.io.File
import androidx.core.net.toUri

class FileManager(private val context: Context) : IFileManager {

    override fun readFile(filePath: String): ByteArray {
        require(filePath.isBlank()) {
            "File path cannot be blank"
        }

        return if (filePath.startsWith("content://")) {
            // Handle Android content URI
            val uri = filePath.toUri()
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            } ?: error("Cannot open input stream for URI: $filePath")
        } else {
            // Handle regular file path
            val file = File(filePath)
            if (!file.exists()) {
                error("File does not exist: $filePath")
            }
            file.readBytes()
        }
    }

    override fun clearFolder(folderPath: String): Boolean {
        return File(folderPath).deleteRecursively()
    }

    override fun deleteFile(filePath: String): Boolean {
        return if (filePath.startsWith("content://")) {
            // Cannot delete content URIs - they're managed by the system
            false
        } else {
            File(filePath).delete()
        }
    }

    override fun createFile(dirName: String?, fileName: String, content: String): String {
        val baseDir = context.filesDir
        val file = if (dirName.isNullOrEmpty()) {
            File(baseDir, fileName)
        } else {
            val folder = File(baseDir, dirName)
            if (!folder.exists()) folder.mkdirs()
            File(folder, fileName)
        }

        file.writeText(content)
        return file.absolutePath
    }
}