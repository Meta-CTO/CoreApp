package com.metacto.catalogapp.utils
///TODO THIS FOR DEMO ONLY  JUST TO CREATE FILES TO GET PATHS

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class FileHandler {
    fun createFolderAndFile(folderName: String, fileName: String, content: String): String
}
