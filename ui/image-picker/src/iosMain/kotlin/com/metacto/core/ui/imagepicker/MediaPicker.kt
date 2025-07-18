package com.metacto.core.ui.imagepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import com.metacto.core.extensions.normalizedImage
import com.metacto.core.extensions.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIModalPresentationFullScreen
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
actual class MediaPicker(
    private val rootController: UIViewController,
    actual val enableCropping: Boolean,
    actual val aspectRatioX: Int?,
    actual val aspectRatioY: Int?
) {
    private val imagePickerController = UIImagePickerController()
    private var onMediaPicked: (MediaInfo) -> Unit = {}
    private var currentSource: MediaInfoSource = MediaInfoSource.Gallery
    private var allowedMediaTypes: List<MediaType> = listOf(MediaType.Image)

    private val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
        UINavigationControllerDelegateProtocol {

        override fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>
        ) {
            // Get media type using extension
            val mediaTypeString = didFinishPickingMediaWithInfo.extractMediaType()
            val isVideo = mediaTypeString.isVideoType()
            
            when {
                isVideo -> {
                    // Handle video using extension
                    didFinishPickingMediaWithInfo.extractVideoURL()?.let { url ->
                        val videoData = url.extractVideoData() ?: ByteArray(0)
                        onMediaPicked(
                            MediaInfo(
                                data = videoData,
                                type = MediaType.Video,
                                filePath = url.safePathString(),
                                source = currentSource
                            )
                        )
                    }
                }
                else -> {
                    // Handle image using extension
                    val image = didFinishPickingMediaWithInfo.extractUIImage(
                        enableCropping && imagePickerController.allowsEditing
                    )
                    
                    image?.normalizedImage()?.toByteArray()?.let { imageBytes ->
                        onMediaPicked(
                            MediaInfo(
                                data = imageBytes,
                                type = MediaType.Image,
                                filePath = "", // iOS doesn't provide direct file path for images
                                source = currentSource
                            )
                        )
                    }
                }
            }
            
            // Dismiss the picker controller
            picker.dismissViewControllerAnimated(true, null)
        }

        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            picker.dismissViewControllerAnimated(true, null)
        }
    }

    @Composable
    actual fun registerPicker(onMediaPicked: (MediaInfo) -> Unit) {
        this.onMediaPicked = onMediaPicked
    }

    actual fun pickFromGallery(mediaTypes: List<MediaType>) {
        currentSource = MediaInfoSource.Gallery
        allowedMediaTypes = mediaTypes
        pickMedia(
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary,
            mediaTypes
        )
    }

    actual fun captureUsingCamera() {
        currentSource = MediaInfoSource.Camera
        pickMedia(
            source = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera,
            mediaTypes = listOf(MediaType.Image)
        )
    }

    private fun pickMedia(source: UIImagePickerControllerSourceType, mediaTypes: List<MediaType>) {
        imagePickerController.sourceType = source
        
        // Set media types using extension
        imagePickerController.mediaTypes = mediaTypes.toMediaTypes()
        
        // Only allow editing for images when cropping is enabled
        imagePickerController.allowsEditing = enableCropping && mediaTypes.size == 1 && mediaTypes.contains(MediaType.Image)
        imagePickerController.setModalPresentationStyle(UIModalPresentationFullScreen)

        rootController.presentViewController(imagePickerController, true) {
            imagePickerController.delegate = delegate
        }
    }
}

@Composable
actual fun rememberMediaPicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?
): MediaPicker {
    val rootController = LocalUIViewController.current
    return remember(enableCropping, aspectRatioX, aspectRatioY) {
        MediaPicker(
            rootController = rootController,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )
    }
}