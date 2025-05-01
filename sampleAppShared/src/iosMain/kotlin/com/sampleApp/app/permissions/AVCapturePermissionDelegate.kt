package com.sampleApp.app.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.ui.permissions.helpers.PermissionDelegate
import com.metacto.core.ui.extensions.mainContinuation
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVAuthorizationStatusRestricted
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaType
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class AVCapturePermissionDelegate(
    private val type: AVMediaType,
    private val permission: Permission
) : PermissionDelegate {

    override suspend fun providePermission() {
        when (val status = currentAuthorizationStatus()) {
            AVAuthorizationStatusAuthorized -> return
            AVAuthorizationStatusNotDetermined -> {
                val isGranted: Boolean = suspendCoroutine { continuation ->
                    AVCaptureDevice.requestAccess(type) { continuation.resume(it) }
                }
                if (isGranted) return
                else throw DeniedAlwaysException(permission)
            }

            AVAuthorizationStatusDenied -> throw DeniedAlwaysException(permission)
            else -> error("unknown authorization status $status")
        }
    }

    override suspend fun getPermissionState(): PermissionState {
        return when (val status = currentAuthorizationStatus()) {
            AVAuthorizationStatusAuthorized -> PermissionState.Granted
            AVAuthorizationStatusNotDetermined -> PermissionState.NotDetermined
            AVAuthorizationStatusDenied -> PermissionState.DeniedAlways
            AVAuthorizationStatusRestricted -> PermissionState.Granted
            else -> error("unknown authorization status $status")
        }
    }

    private fun currentAuthorizationStatus(): AVAuthorizationStatus {
        return AVCaptureDevice.authorizationStatusForMediaType(type)
    }
}

private fun AVCaptureDevice.Companion.requestAccess(
    type: AVMediaType,
    callback: (isGranted: Boolean) -> Unit
) {
    this.requestAccessForMediaType(type, mainContinuation { isGranted: Boolean ->
        callback(isGranted)
    })
}