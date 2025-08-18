package com.metacto.core.ui.imagepicker.sheet.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import com.metacto.core.ui.components.bottomSheets.BottomSheetContainer
import com.metacto.core.ui.components.options.OptionItem
import com.metacto.core.ui.components.options.models.OptionUIModel
import com.metacto.core.ui.theme.CoreTheme
import com.metacto.core.ui.extensions.toHexString
import com.metacto.core.ui.imagepicker.resources.Res
import com.metacto.core.ui.imagepicker.resources.choose_from_library
import com.metacto.core.ui.imagepicker.resources.ic_camera
import com.metacto.core.ui.imagepicker.resources.ic_delete
import com.metacto.core.ui.imagepicker.resources.ic_photo_library
import com.metacto.core.ui.imagepicker.resources.remove_current_photo
import com.metacto.core.ui.imagepicker.resources.take_photo
import org.jetbrains.compose.resources.stringResource
import com.metacto.core.ui.imagepicker.sheet.ImagePickerContract.State
import com.metacto.core.ui.imagepicker.sheet.ImagePickerContract.Event

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