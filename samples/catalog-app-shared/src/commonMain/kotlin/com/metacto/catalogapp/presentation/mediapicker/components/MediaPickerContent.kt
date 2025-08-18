package com.metacto.catalogapp.presentation.mediaPicker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.mediaPicker.MediaPickerContract.Event
import com.metacto.catalogapp.presentation.mediaPicker.MediaPickerContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.catalogapp.resources.Res
import com.metacto.catalogapp.resources.ic_star_filled
import com.metacto.core.files.IFileManager
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.images.AppImage
import com.metacto.core.ui.extensions.rememberIOCoroutineScope
import com.metacto.core.ui.imagepicker.MediaInfo
import com.metacto.core.ui.imagepicker.MediaType
import com.metacto.core.ui.imagepicker.rememberMediaPicker
import com.metacto.core.ui.media.IMediaManager
import com.metacto.core.ui.models.ImageUIModel
import com.metacto.core.ui.navigation.NavManager
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
internal fun MediaPickerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val mediaManager = koinInject<IMediaManager>()
    val fileManager = koinInject<IFileManager>()
    val mediaPicker = rememberMediaPicker(includeData = false)
    val coroutineScope = rememberIOCoroutineScope()

    // States
    var medias by remember { mutableStateOf(listOf<MediaInfo>()) }
    var videoPreviews by remember { mutableStateOf(emptyMap<String, ByteArray>()) }
    
    // Register pickers
    mediaPicker.registerPicker { mediaInfo ->
        medias = medias + mediaInfo

        if (mediaInfo.type == MediaType.Video) {
            coroutineScope.launch {
                val videoPreview = mediaManager.getVideoPreview(mediaInfo.filePath)
                videoPreview?.let { preview ->
                    videoPreviews = videoPreviews + (mediaInfo.filePath to preview)
                }
            }
        }
    }

    // Container column
    AppScreenColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing8),
        title = "Media Picker",
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
                mediaPicker.pickFromGallery(listOf(MediaType.Image))
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Pick Video",
            onClick = {
                mediaPicker.pickFromGallery(listOf(MediaType.Video))
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Pick Image/Video",
            onClick = {
                mediaPicker.pickFromGallery(listOf(MediaType.Image, MediaType.Video))
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Fetch medias data",
            onClick = {
                coroutineScope.launch {
                    medias.forEach { media ->
                        try {
                            val bytes = fileManager.readFile(media.filePath)
                            println("Successfully read bytes for ${media.filePath}: ${bytes.size} bytes")
                        } catch (e: Throwable) {
                            println("Error reading file ${media.filePath}: ${e.message}")
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        medias.forEach { mediaInfo ->
            when(mediaInfo.type) {
                MediaType.Image -> {
                    AppImage(
                        image = ImageUIModel(filePath = mediaInfo.filePath),
                        placeholderPainter = painterResource(Res.drawable.ic_star_filled),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(150.dp)
                    )
                }

                MediaType.Video -> {
                    AppImage(
                        image = ImageUIModel(bytes = videoPreviews[mediaInfo.filePath]),
                        placeholderPainter = painterResource(Res.drawable.ic_star_filled),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(150.dp)
                    )
                }
            }
        }
    }
}