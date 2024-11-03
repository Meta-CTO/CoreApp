package com.metacto.core.presentation.imagePicker.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import com.metacto.coreApp.resources.*
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.options.OptionItem
import com.metacto.core.presentation.options.models.OptionUIModel
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.core.presentation.imagePicker.ImagePickerContract.State
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.extensions.toHexString
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImagePickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Bottom sheet container
    BottomSheetContainer(
        startIcon = Icons.Default.Close,
        onStartIconClick = {
            onEvent(Event.CloseClicked)
        }
    ) {
        // Render gallery option if required
        if (state.allowGallery) {
            OptionItem(
                option = OptionUIModel(
                    title = stringResource(Res.string.choose_from_library),
                    icon = Res.drawable.ic_photo_library
                ),
                onClick = {
                    onEvent(Event.PickFromGalleryClicked)
                }
            )
        }

        // Render camera option if required
        if (state.allowCamera) {
            OptionItem(
                option = OptionUIModel(
                    title = stringResource(Res.string.take_photo),
                    icon = Res.drawable.ic_camera
                ),
                onClick = {
                    onEvent(Event.CaptureUsingCameraClicked)
                }
            )
        }

        // Render delete option if required
        if (state.showDeleteAction) {
            OptionItem(
                option = OptionUIModel(
                    title = stringResource(Res.string.remove_current_photo),
                    icon = Res.drawable.ic_delete,
                    color = CoreTheme.colors.danger.toHexString(withAlpha = true)
                ),
                onClick = {
                    onEvent(Event.DeleteCurrentPhotoClicked)
                }
            )
        }
    }
}