package com.sampleApp.app.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.ui.permissions.helpers.PermissionDelegate
import com.metacto.core.ui.extensions.mainContinuation
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class GalleryPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() {
        providePermission(PHPhotoLibrary.authorizationStatus())
    }

    private suspend fun providePermission(status: PHAuthorizationStatus) {
        return when (status) {
            PHAuthorizationStatusAuthorized -> return
            PHAuthorizationStatusNotDetermined -> {
                val newStatus = suspendCoroutine { continuation ->
                    requestGalleryAccess { continuation.resume(it) }
                }
                providePermission(newStatus)
            }

            PHAuthorizationStatusDenied -> throw DeniedAlwaysException(Permission.GALLERY)
            else -> error("unknown gallery authorization status $status")
        }
    }

    override suspend fun getPermissionState(): PermissionState {
        return when (val status = PHPhotoLibrary.authorizationStatus()) {
            PHAuthorizationStatusAuthorized -> PermissionState.Granted
            PHAuthorizationStatusNotDetermined -> PermissionState.NotDetermined
            PHAuthorizationStatusDenied -> PermissionState.DeniedAlways
            else -> error("unknown gallery authorization status $status")
        }
    }
}

private fun requestGalleryAccess(callback: (PHAuthorizationStatus) -> Unit) {
    PHPhotoLibrary.requestAuthorization(mainContinuation { status: PHAuthorizationStatus ->
        callback(status)
    })
}
