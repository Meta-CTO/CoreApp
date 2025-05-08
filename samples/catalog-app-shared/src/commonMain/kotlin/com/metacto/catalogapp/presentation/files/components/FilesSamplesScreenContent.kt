package com.metacto.catalogapp.presentation.files.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.Event
import com.metacto.catalogapp.presentation.files.FilesSamplesContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.files.IFileManager
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.globalState.ICoreGlobalState
import com.metacto.core.ui.globalState.models.SnackBarParams
import com.metacto.core.ui.globalState.models.SnackBarType
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject


@Composable
internal fun FilesSamplesScreenContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val fileManager = koinInject<IFileManager>()
    val coreGlobalState = koinInject<ICoreGlobalState>()

    // state
    var filePath by remember { mutableStateOf("") }

    // Container column
    AppScreenColumn(
        title = "FilesSamplesScreen",
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
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "Folder and file created successfully: $path",
                            type = SnackBarType.SUCCESS,
                        )
                    )
                } else {
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "Failed to create folder and file",
                            type = SnackBarType.ERROR,
                        )
                    )
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

                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "File read successfully: $filePath",
                            type = SnackBarType.SUCCESS,
                        )
                    )

                } catch (e: Throwable) {
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "Failed to read file: $filePath, ${e.message}",

                            type = SnackBarType.ERROR,
                        )
                    )
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
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "Folder cleared successfully",
                            type = SnackBarType.SUCCESS,
                        )
                    )
                } else {
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "Failed to clear folder",
                            type = SnackBarType.ERROR,
                        )
                    )
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
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "File deleted successfully",
                            type = SnackBarType.SUCCESS,
                        )
                    )
                } else {
                    coreGlobalState.snackBar(
                        SnackBarParams(
                            message = "Failed to delete file",
                            type = SnackBarType.ERROR,
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )
    }
}
