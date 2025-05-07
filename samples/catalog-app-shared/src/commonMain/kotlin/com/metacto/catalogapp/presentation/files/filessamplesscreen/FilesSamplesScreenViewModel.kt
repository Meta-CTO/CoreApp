package com.metacto.catalogapp.presentation.files.filessamplesscreen

import com.metacto.catalogapp.presentation.base.BaseViewModel
import com.metacto.catalogapp.presentation.files.filessamplesscreen.FilesSamplesScreenContract.Effect
import com.metacto.catalogapp.presentation.files.filessamplesscreen.FilesSamplesScreenContract.Event
import com.metacto.catalogapp.presentation.files.filessamplesscreen.FilesSamplesScreenContract.State
import com.metacto.catalogapp.utils.FileHandler
import com.metacto.core.files.IFileManager
import com.metacto.core.ui.globalState.models.SnackBarParams
import com.metacto.core.ui.globalState.models.SnackBarType
import org.koin.core.component.inject

class FilesSamplesScreenViewModel : BaseViewModel<State, Event, Effect>() {
    private val fileManager by inject<IFileManager>()
    val fileHandler by inject<FileHandler>()
    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.CreateFolderAndFile -> handleCreateFolderAndFile()
        is Event.ReadFile -> handleReadFile(event.path)
        is Event.ClearFolder -> handleClearFolder(event.path)
        is Event.DeleteFile -> handleDeleteFile(event.path)
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init

        // Update the flag
        setState { copy(isInitialized = true) }
    }

    ///TODO THIS FOR DEMO ONLY  JUST TO CREATE FILES TO GET PATHS
    private fun handleCreateFolderAndFile() {
        val path = fileHandler.createFolderAndFile("MyFolder", "note.txt", "This is a test.")
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
        setState { copy(filePath = path) }
    }

    private fun handleReadFile(path: String) = executeSilent({
        val bytes = fileManager.readFile(path)

        if (bytes.isNotEmpty()) {
            coreGlobalState.snackBar(
                SnackBarParams(
                    message = "File read successfully: $path",
                    type = SnackBarType.SUCCESS,
                )
            )
        }
    })

    private fun handleClearFolder(path: String) {
        val boolean = fileManager.clearFolder(path)
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
    }

    private fun handleDeleteFile(path: String) {
        val boolean = fileManager.deleteFile(path)
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
    }
}
