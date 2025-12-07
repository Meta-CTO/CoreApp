package com.metacto.core.ui.imagepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import com.metacto.core.extensions.normalizedImage
import com.metacto.core.extensions.toByteArray
import com.metacto.core.ui.imagepicker.crop.presentCropViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIImage
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
    actual val aspectRatioY: Int?,
    actual val includeData: Boolean
) {
    private val imagePickerController = UIImagePickerController()
    private var onMediaPicked: (MediaInfo) -> Unit = {}
    private var currentSource: MediaInfoSource = MediaInfoSource.Gallery
    private var allowedMediaTypes: List<MediaType> = listOf(MediaType.Image)
    private val createdMediaInfos = mutableSetOf<MediaInfo>()

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
                        // For iOS videos, always read the data immediately since temp files get deleted
                        val videoData = url.extractVideoData()
                        // Always save to temp file for file path, regardless of includeData
                        val tempFilePath = videoData?.let { saveVideoToTempFile(it) } ?: ""
                        val mediaInfo = MediaInfo(
                            data = if (includeData) videoData else null,
                            type = MediaType.Video,
                            filePath = tempFilePath,
                            source = currentSource
                        )
                        createdMediaInfos.add(mediaInfo)
                        onMediaPicked(mediaInfo)
                    }
                    // Dismiss the picker controller
                    picker.dismissViewControllerAnimated(true, null)
                }
                else -> {
                    // Handle image using extension
                    // Don't use built-in editing if we have custom aspect ratio
                    val useBuiltInEditing = enableCropping &&
                                           imagePickerController.allowsEditing &&
                                           (aspectRatioX == null || aspectRatioY == null ||
                                            aspectRatioX == 1 && aspectRatioY == 1)

                    val image = didFinishPickingMediaWithInfo.extractUIImage(useBuiltInEditing)

                    image?.let { uiImage ->
                        val normalizedImage = uiImage.normalizedImage()

                        // Check if we should show custom crop UI
                        val shouldShowCustomCrop = enableCropping &&
                                                   aspectRatioX != null &&
                                                   aspectRatioY != null &&
                                                   !(aspectRatioX == 1 && aspectRatioY == 1)

                        if (shouldShowCustomCrop) {
                            // Dismiss picker first, then present crop UI in completion handler
                            picker.dismissViewControllerAnimated(true) {
                                presentCropViewController(
                                    parentController = rootController,
                                    image = normalizedImage,
                                    aspectRatioX = aspectRatioX,
                                    aspectRatioY = aspectRatioY,
                                    onCropComplete = { croppedImage ->
                                        processAndReturnImage(croppedImage)
                                    },
                                    onCancel = {
                                        // User cancelled cropping, do nothing
                                    }
                                )
                            }
                        } else {
                            // Process image directly and dismiss
                            processAndReturnImage(normalizedImage)
                            picker.dismissViewControllerAnimated(true, null)
                        }
                    }
                }
            }
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

        // Disable built-in editing if we're using custom crop UI
        val useCustomCrop = enableCropping &&
                           aspectRatioX != null &&
                           aspectRatioY != null &&
                           !(aspectRatioX == 1 && aspectRatioY == 1)

        imagePickerController.allowsEditing = enableCropping &&
                                             !useCustomCrop &&
                                             mediaTypes.size == 1 &&
                                             mediaTypes.contains(MediaType.Image)
        imagePickerController.setModalPresentationStyle(UIModalPresentationFullScreen)

        // Set delegate BEFORE presenting
        imagePickerController.delegate = delegate

        rootController.presentViewController(imagePickerController, true, null)
    }

    internal fun processAndReturnImage(image: UIImage) {
        val imageData = if (includeData) image.toByteArray() else null
        val filePath = image.saveToTemporaryFile()

        val mediaInfo = MediaInfo(
            data = imageData,
            type = MediaType.Image,
            filePath = filePath.orEmpty(),
            source = currentSource
        )
        createdMediaInfos.add(mediaInfo)
        onMediaPicked(mediaInfo)
    }

    internal actual fun cleanup() {
        createdMediaInfos.forEach { mediaInfo ->
            mediaInfo.cleanupTemporaryFiles()
        }
        createdMediaInfos.clear()
    }
}

@Composable
actual fun rememberMediaPicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?,
    includeData: Boolean
): MediaPicker {
    val rootController = LocalUIViewController.current
    val mediaPicker = remember(enableCropping, aspectRatioX, aspectRatioY, includeData) {
        MediaPicker(
            rootController = rootController,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY,
            includeData = includeData
        )
    }
    
    DisposableEffect(mediaPicker) {
        onDispose {
            mediaPicker.cleanup()
        }
    }
    
    return mediaPicker
}