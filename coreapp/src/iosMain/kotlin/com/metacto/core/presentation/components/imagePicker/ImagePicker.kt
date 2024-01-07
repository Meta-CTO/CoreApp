@file:OptIn(ExperimentalForeignApi::class, ExperimentalForeignApi::class)
package com.metacto.core.presentation.components.imagePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIModalPresentationPopover
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.posix.memcpy

actual class ImagePicker(
    private val rootController: UIViewController,
    actual val enableCropping: Boolean,
    actual val aspectRatioX: Int?,
    actual val aspectRatioY: Int?
) {
    private val imagePickerController = UIImagePickerController()
    private var onImagePicked: (ByteArray) -> Unit = {}

    private val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
        UINavigationControllerDelegateProtocol {

        override fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingImage: UIImage,
            editingInfo: Map<Any?, *>?
        ) {
            // Get picked image
            val imageNsData = UIImageJPEGRepresentation(didFinishPickingImage, 1.0)
                ?: return
            val bytes = ByteArray(imageNsData.length.toInt())
            memcpy(bytes.refTo(0), imageNsData.bytes, imageNsData.length)

            // Dismiss the picker controller
            picker.dismissViewControllerAnimated(true, null)

            // Check if cropping is required
            if (enableCropping) {
                val ratioX = aspectRatioX ?: 1
                val ratioY = aspectRatioY ?: 1
                // TODO: open cropper and wait for result then notify onImagePicked(bytes)
                onImagePicked(bytes)
            } else {
                onImagePicked(bytes)
            }
        }

        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            picker.dismissViewControllerAnimated(true, null)
        }
    }

    @Composable
    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
    }

    actual fun pickFromGallery() {
        pickImage(
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
        )
    }

    actual fun captureUsingCamera() {
        pickImage(
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
        )
    }

    private fun pickImage(source: UIImagePickerControllerSourceType) {
        imagePickerController.sourceType = source
        imagePickerController.setModalPresentationStyle(UIModalPresentationPopover)
        rootController.presentViewController(imagePickerController, true) {
            imagePickerController.delegate = delegate
        }
    }
}

@Composable
actual fun rememberImagePicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?
): ImagePicker {
    val rootController = LocalUIViewController.current
    return remember {
        ImagePicker(
            rootController = rootController,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )
    }
}