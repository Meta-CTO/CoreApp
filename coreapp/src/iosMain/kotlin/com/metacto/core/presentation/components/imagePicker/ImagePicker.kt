package com.metacto.core.presentation.components.imagePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import com.metacto.core.utils.extensions.normalizedImage
import com.metacto.core.utils.extensions.toByteArray
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIModalPresentationFullScreen
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject

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
            // Get picked image bytes
            val imageBytes = didFinishPickingImage.normalizedImage().toByteArray() ?: return

            // Dismiss the picker controller
            picker.dismissViewControllerAnimated(true, null)

            // And notify image picked
            onImagePicked(imageBytes)
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
        imagePickerController.allowsImageEditing = enableCropping
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