package com.metacto.catalogapp.presentation.files.filessamplesscreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.files.filessamplesscreen.FilesSamplesScreenContract.Event
import com.metacto.catalogapp.presentation.files.filessamplesscreen.FilesSamplesScreenContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject


@Composable
internal fun FilesSamplesScreenContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

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
        ///TODO THIS FOR DEMO ONLY JUST TO CREATE FILES TO GET PATHS
        // create folder
        PrimaryFilledButton(
            text = "Create Folder",
            onClick = {
                onEvent(Event.CreateFolderAndFile)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // read file
        PrimaryFilledButton(
            text = "Read File",
            onClick = {
                onEvent(Event.ReadFile(path = state.filePath))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        //clear file
        PrimaryFilledButton(
            text = "Clear File",
            onClick = {
                onEvent(Event.ClearFolder(path = state.filePath))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        //delete file
        PrimaryFilledButton(
            text = "Delete File",
            onClick = {
                onEvent(Event.DeleteFile(path = state.filePath))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )
    }
}
