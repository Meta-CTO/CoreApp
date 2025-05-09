package com.metacto.catalogapp.presentation.imagePicker.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerContract.Event
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.images.AppImage
import com.metacto.core.ui.imagepicker.sheet.ImagePickerSheet
import com.metacto.core.ui.imagepicker.sheet.models.ImagePickerResult
import com.metacto.core.ui.models.ImageUIModel
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun ImagePickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // state
    var image by remember { mutableStateOf(ImageUIModel()) }

    // Observe result from ImagePickerSheet
    LaunchedEffect(Unit) {
        navManager.onNavResult<ImagePickerSheet, ImagePickerResult> {
            when (it) {
                is ImagePickerResult.ImagePicked -> {
                    image = image.copy(
                        bytes = it.bytes
                    )
                }

                ImagePickerResult.ImageDeleted -> {
                    image = ImageUIModel()

                }

                else -> {}
            }
        }
    }

// Container column
    AppScreenColumn(
        title = "ImagePicker",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        PrimaryFilledButton(
            text = "Pick Image",
            onClick = {
                navManager.navigateToBottomSheet(ImagePickerSheet())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacings.spacing16)
        )
        AppImage(
            image = image,
            contentDescription = null,
            modifier = Modifier
                .size(spacings.spacing100)
                .padding(spacings.spacing16)
        )

    }
}

