package com.metacto.catalogapp.presentation.files.filessamplesscreen

import com.metacto.core.ui.base.ViewEvent
import com.metacto.core.ui.base.ViewSideEffect
import com.metacto.core.ui.base.ViewState

class FilesSamplesScreenContract {

    data class State(
        val isInitialized: Boolean = false,
        val filePath: String = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object CreateFolderAndFile : Event()
        data class ReadFile(val path: String) : Event()
        data class ClearFolder(val path: String) : Event()
        data class DeleteFile(val path: String) : Event()
    }

    sealed class Effect : ViewSideEffect
}
