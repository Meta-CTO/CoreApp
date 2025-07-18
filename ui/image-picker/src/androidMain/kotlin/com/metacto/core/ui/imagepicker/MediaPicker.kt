package com.metacto.core.ui.imagepicker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.github.dhaval2404.imagepicker.ImagePicker as SdkImagePicker

actual class MediaPicker(
    private val activity: Activity,
    actual val enableCropping: Boolean,
    actual val aspectRatioX: Int?,
    actual val aspectRatioY: Int?,
    actual val includeData: Boolean
) {
    private var onMediaPicked: ((MediaInfo) -> Unit)? = null
    private var pickerLauncher: ActivityResultLauncher<Intent>? = null
    private var cropperLauncher: ActivityResultLauncher<CropImageContractOptions>? = null
    private var currentSource: MediaInfoSource = MediaInfoSource.Gallery

    @SuppressLint("ComposableNaming")
    @Composable
    actual fun registerPicker(onMediaPicked: (MediaInfo) -> Unit) {
        // Set onImagePicked
        this.onMediaPicked = onMediaPicked

        // Create image picker launcher
        pickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val uri = result.data?.data
            if (result.resultCode == Activity.RESULT_OK && uri != null) {
                val mediaType = uri.getMediaType(activity)
                val isVideo = mediaType == MediaType.Video
                
                if (enableCropping && !isVideo) {
                    cropImage(uri)
                } else {
                    notifyMediaPicked(uri)
                }
            }
        }

        // Create cropper launcher if required
        if (enableCropping) {
            cropperLauncher = rememberLauncherForActivityResult(
                contract = CropImageContract()
            ) { result ->
                val uri = result.uriContent
                if (result.isSuccessful && uri != null) {
                    notifyMediaPicked(uri)
                }
            }
        }
    }

    actual fun pickFromGallery(mediaTypes: List<MediaType>) {
        currentSource = MediaInfoSource.Gallery
        SdkImagePicker.with(activity)
            .galleryOnly()
            .galleryMimeTypes(mediaTypes.mimeTypes().toTypedArray())
            .createIntent { intent ->
                pickerLauncher?.launch(intent)
            }
    }

    actual fun captureUsingCamera() {
        currentSource = MediaInfoSource.Camera
        SdkImagePicker.with(activity)
            .cameraOnly()
            .createIntent { intent ->
                pickerLauncher?.launch(intent)
            }
    }

    private fun cropImage(uri: Uri) {
        cropperLauncher?.launch(
            CropImageContractOptions(
                uri,
                CropImageOptions(
                    imageSourceIncludeGallery = false,
                    imageSourceIncludeCamera = false,
                    aspectRatioY = aspectRatioY ?: 1,
                    aspectRatioX = aspectRatioX ?: 1,
                    fixAspectRatio = true
                )
            )
        )
    }

    private fun notifyMediaPicked(uri: Uri) {
        val mediaType = uri.getMediaType(activity)
        
        if (includeData) {
            // Load the media data into memory
            activity.contentResolver.openInputStream(uri)?.use {
                onMediaPicked?.invoke(
                    MediaInfo(
                        data = it.readBytes(),
                        type = mediaType,
                        filePath = uri.toString(),
                        source = currentSource
                    )
                )
            }
        } else {
            // Return only the file path, no data
            onMediaPicked?.invoke(
                MediaInfo(
                    type = mediaType,
                    filePath = uri.toString(),
                    source = currentSource
                )
            )
        }
    }
}

@Composable
actual fun rememberMediaPicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?,
    includeData: Boolean
): MediaPicker {
    val activity = LocalContext.current as Activity
    return remember(activity) {
        MediaPicker(
            activity = activity,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY,
            includeData = includeData
        )
    }
}