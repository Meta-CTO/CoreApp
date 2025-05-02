package com.metacto.catalogapp.utils

import android.content.Context
import java.io.File

///TODO THIS FOR DEMO ONLY  JUST TO CREATE FILES TO GET PATHS

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class FileHandler(private val context: Context) {
    actual fun createFolderAndFile(folderName: String, fileName: String, content: String): String {
        val folder = File(context.filesDir, folderName)
        if (!folder.exists()) folder.mkdirs()

        val file = File(folder, fileName)
        file.writeText(content)

        return file.absolutePath
    }
}
