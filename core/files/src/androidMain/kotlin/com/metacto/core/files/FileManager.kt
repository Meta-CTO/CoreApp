package com.metacto.core.files

import android.content.Context
import java.io.File

class FileManager(private val context: Context) : IFileManager {

    override fun readFile(filePath: String): ByteArray {
        return File(filePath).readBytes()
    }

    override fun clearFolder(folderPath: String): Boolean {
        return File(folderPath).deleteRecursively()
    }

    override fun deleteFile(filePath: String): Boolean {
        return File(filePath).delete()
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