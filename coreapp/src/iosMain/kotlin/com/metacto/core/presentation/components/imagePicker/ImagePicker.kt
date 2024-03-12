@file:OptIn(ExperimentalForeignApi::class)

package com.metacto.core.presentation.components.imagePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImageOrientation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIModalPresentationFullScreen
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
            val imageNsData =
                UIImageJPEGRepresentation(didFinishPickingImage.normalizedImage(), 1.0)
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
        imagePickerController.setModalPresentationStyle(UIModalPresentationFullScreen)
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
    return remember(enableCropping, aspectRatioX, aspectRatioY) {
        ImagePicker(
            rootController = rootController,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )
    }
}

fun UIImage.normalizedImage(): UIImage {
    if (this.imageOrientation == UIImageOrientation.UIImageOrientationUp) return this

    val size = this.size
    var width = 0.0
    var height = 0.0
    size.useContents {
        width = this.width
        height = this.height
    }
    val scale = this.scale

    UIGraphicsBeginImageContextWithOptions(size, false, scale)
    this.drawInRect(CGRectMake(0.0, 0.0, width, height))
    val normalizedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return normalizedImage ?: this
}