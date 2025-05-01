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

actual class ImagePicker(
    private val activity: Activity,
    actual val enableCropping: Boolean,
    actual val aspectRatioX: Int?,
    actual val aspectRatioY: Int?
) {
    private var onImagePicked: ((ByteArray) -> Unit)? = null

    private var pickerLauncher: ActivityResultLauncher<Intent>? = null
    private var cropperLauncher: ActivityResultLauncher<CropImageContractOptions>? = null

    @SuppressLint("ComposableNaming")
    @Composable
    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
        // Set onImagePicked
        this.onImagePicked = onImagePicked

        // Create image picker launcher
        pickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val uri = result.data?.data
            if (result.resultCode == Activity.RESULT_OK && uri != null) {
                if (enableCropping) {
                    cropImage(uri)
                } else {
                    notifyImagePicked(uri)
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
                    notifyImagePicked(uri)
                }
            }
        }
    }

    actual fun pickFromGallery() {
        SdkImagePicker.with(activity)
            .galleryOnly()
            .createIntent { intent ->
                pickerLauncher?.launch(intent)
            }
    }

    actual fun captureUsingCamera() {
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

    private fun notifyImagePicked(uri: Uri) {
        activity.contentResolver.openInputStream(uri)?.use {
            onImagePicked?.invoke(it.readBytes())
        }
    }
}

@Composable
actual fun rememberImagePicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?
): ImagePicker {
    val activity = LocalContext.current as Activity
    return remember(activity) {
        ImagePicker(
            activity = activity,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )
    }
}