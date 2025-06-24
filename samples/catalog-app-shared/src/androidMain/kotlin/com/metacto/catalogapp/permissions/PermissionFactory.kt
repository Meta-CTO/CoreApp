package com.metacto.catalogapp.permissions

import android.Manifest
import android.os.Build
import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.helpers.IPermissionFactory

class PermissionFactory : IPermissionFactory {

    override fun getPermissions(permission: Permission): List<String> {
        return when(permission) {
            Permission.REMOTE_NOTIFICATION -> remoteNotificationsPermissions()
            Permission.CAMERA -> listOf(Manifest.permission.CAMERA)
            Permission.GALLERY -> galleryCompat()
            Permission.STORAGE -> allStoragePermissions()

            else -> error(
                "Permission $permission is not supported. Please implement the required permissions in the PermissionFactory."
            )
        }
    }

    private fun galleryCompat(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }


    private fun allStoragePermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun remoteNotificationsPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyList()
        }
    }
}