package com.metacto.core.presentation.imagePicker.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import com.metacto.coreApp.MR
import com.metacto.core.presentation.components.bottomSheets.BottomSheetContainer
import com.metacto.core.presentation.components.options.OptionItem
import com.metacto.core.presentation.options.models.OptionUIModel
import com.metacto.core.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.core.presentation.imagePicker.ImagePickerContract.State
import com.metacto.core.presentation.theme.CoreTheme
import com.metacto.core.utils.asCommon
import dev.icerock.moko.resources.compose.stringResource

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
                    title = stringResource(MR.strings.choose_from_library),
                    icon = MR.images.ic_photo_library.asCommon()
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
                    title = stringResource(MR.strings.take_photo),
                    icon = MR.images.ic_camera.asCommon()
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
                    title = stringResource(MR.strings.remove_current_photo),
                    icon = MR.images.ic_delete.asCommon(),
                    color = CoreTheme.colors.danger.value
                ),
                onClick = {
                    onEvent(Event.DeleteCurrentPhotoClicked)
                }
            )
        }
    }
}