package com.metacto.catalogapp.presentation.files.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.Event
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.files.IFileManager
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun FilesSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val fileManager = koinInject<IFileManager>()
    val globalState = koinInject<IAppGlobalState>()

    // state
    var filePath by remember { mutableStateOf("") }

    // Container column
    AppScreenColumn(
        title = "Files Samples",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        // create folder
        PrimaryFilledButton(
            text = "Create Folder",
            onClick = {
                val path = fileManager.createFile(
                    fileName = "note.txt", content = "test", dirName = "notes"
                )
                if (path.isNotEmpty()) {
                    globalState.showSuccess("Folder and file created successfully: $path")
                } else {
                    globalState.showError("Failed to create folder and file")
                }
                filePath = path
            },
            modifier = Modifier.fillMaxWidth()
        )

        // read file
        PrimaryFilledButton(
            text = "Read File",
            onClick = {
                try {
                    fileManager.readFile(filePath)

                    globalState.showSuccess("File read successfully: $filePath")

                } catch (e: Throwable) {
                    globalState.showError("Failed to read file: $filePath, ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        //clear file
        PrimaryFilledButton(
            text = "Clear File",
            onClick = {
                val boolean = fileManager.clearFolder(filePath)
                if (boolean) {
                    globalState.showSuccess("Folder cleared successfully")
                } else {
                    globalState.showError("Failed to clear folder")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        //delete file
        PrimaryFilledButton(
            text = "Delete File",
            onClick = {
                val boolean = fileManager.deleteFile(filePath)
                if (boolean) {
                    globalState.showSuccess("File deleted successfully")
                } else {
                    globalState.showError("Failed to delete file")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )
    }
}
