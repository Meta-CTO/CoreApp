package com.metacto.core.ui.imagepicker.crop

import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImage
import platform.UIKit.UIModalPresentationFullScreen
import platform.UIKit.UIViewController

/**
 * Creates and presents a crop UI as a UIViewController
 */
@OptIn(ExperimentalForeignApi::class)
fun presentCropViewController(
    parentController: UIViewController,
    image: UIImage,
    aspectRatioX: Int?,
    aspectRatioY: Int?,
    onCropComplete: (UIImage) -> Unit,
    onCancel: () -> Unit
) {
    val imageBitmap = image.toImageBitmap()

    if (imageBitmap == null) {
        onCancel()
        return
    }

    // Create a Compose UI view controller
    val cropViewController = ComposeUIViewController {
        ImageCropView(
            image = image,
            imageBitmap = imageBitmap,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY,
            onCropComplete = { croppedImage ->
                parentController.dismissViewControllerAnimated(true) {
                    onCropComplete(croppedImage)
                }
            },
            onCancel = {
                parentController.dismissViewControllerAnimated(true) {
                    onCancel()
                }
            }
        )
    }

    // Present modally
    cropViewController.setModalPresentationStyle(UIModalPresentationFullScreen)
    parentController.presentViewController(cropViewController, animated = true, completion = null)
}