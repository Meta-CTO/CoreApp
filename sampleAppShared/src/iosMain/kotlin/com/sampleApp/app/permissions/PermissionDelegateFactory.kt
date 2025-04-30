package com.sampleApp.app.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.ui.permissions.helpers.AlwaysGrantedPermissionDelegate
import com.metacto.core.ui.permissions.helpers.IPermissionDelegateFactory
import com.metacto.core.ui.permissions.helpers.PermissionDelegate
import platform.AVFoundation.AVMediaTypeAudio
import platform.AVFoundation.AVMediaTypeVideo
import platform.Contacts.CNContactStore

class PermissionDelegateFactory : IPermissionDelegateFactory {
    private val locationManagerDelegate = LocationManagerDelegate()
    private val contactStore = CNContactStore()

    override fun getDelegate(permission: Permission): PermissionDelegate {
        return when (permission) {
            Permission.CAMERA -> AVCapturePermissionDelegate(
                type = AVMediaTypeVideo,
                permission = permission
            )

            Permission.RECORD_AUDIO -> AVCapturePermissionDelegate(
                type = AVMediaTypeAudio,
                permission = permission
            )

            Permission.CONTACTS -> ContactsPermissionDelegate(
                permission = permission,
                contactStore = contactStore
            )

            Permission.LOCATION,
            Permission.COARSE_LOCATION,
            Permission.BACKGROUND_LOCATION -> LocationPermissionDelegate(
                locationManagerDelegate = locationManagerDelegate,
                permission = permission
            )

            Permission.BLUETOOTH_LE,
            Permission.BLUETOOTH_SCAN,
            Permission.BLUETOOTH_ADVERTISE,
            Permission.BLUETOOTH_CONNECT -> BluetoothPermissionDelegate(
                permission = permission
            )

            Permission.REMOTE_NOTIFICATION -> RemoteNotificationPermissionDelegate()
            Permission.GALLERY -> GalleryPermissionDelegate()
            Permission.STORAGE, Permission.WRITE_STORAGE -> AlwaysGrantedPermissionDelegate()
            Permission.MOTION -> MotionPermissionDelegate()
            Permission.CALENDER -> AlwaysGrantedPermissionDelegate()
        }
    }
}