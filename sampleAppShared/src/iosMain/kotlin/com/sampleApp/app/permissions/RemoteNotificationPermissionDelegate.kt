package com.sampleApp.app.permissions

import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.permissions.helpers.PermissionDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatus
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusEphemeral
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNAuthorizationStatusProvisional
import platform.UserNotifications.UNNotificationSettings
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.suspendCoroutine

internal class RemoteNotificationPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() {
        val currentCenter: UNUserNotificationCenter = UNUserNotificationCenter
            .currentNotificationCenter()

        val status: UNAuthorizationStatus = suspendCoroutine { continuation ->
            currentCenter.getNotificationSettingsWithCompletionHandler { settings: UNNotificationSettings? ->
                GlobalScope.launch(Dispatchers.Main) {
                    continuation.resumeWith(
                        Result.success(
                            settings?.authorizationStatus ?: UNAuthorizationStatusNotDetermined
                        )
                    )
                }
            }
        }
        when (status) {
            UNAuthorizationStatusAuthorized -> return
            UNAuthorizationStatusNotDetermined -> {
                val isSuccess = suspendCoroutine<Boolean> { continuation ->
                    UNUserNotificationCenter.currentNotificationCenter()
                        .requestAuthorizationWithOptions(
                            UNAuthorizationOptionSound
                                .or(UNAuthorizationOptionAlert)
                                .or(UNAuthorizationOptionBadge)
                        ) { isOk, error ->
                            GlobalScope.launch(Dispatchers.Main) {
                                if (isOk && error == null) {
                                    continuation.resumeWith(Result.success(true))
                                } else {
                                    continuation.resumeWith(Result.success(false))
                                }
                            }
                        }
                }
                if (isSuccess) {
                    providePermission()
                } else {
                    error("notifications permission failed")
                }
            }

            UNAuthorizationStatusDenied -> throw DeniedAlwaysException(Permission.REMOTE_NOTIFICATION)
            else -> error("notifications permission status $status")
        }
    }

    override suspend fun getPermissionState(): PermissionState {
        val currentCenter = UNUserNotificationCenter.currentNotificationCenter()

        val status = suspendCoroutine { continuation ->
            currentCenter.getNotificationSettingsWithCompletionHandler {
                GlobalScope.launch(Dispatchers.Main) {
                    continuation.resumeWith(
                        Result.success(
                            it?.authorizationStatus ?: UNAuthorizationStatusNotDetermined
                        )
                    )
                }
            }
        }
        return when (status) {
            UNAuthorizationStatusAuthorized,
            UNAuthorizationStatusProvisional,
            UNAuthorizationStatusEphemeral -> PermissionState.Granted
            UNAuthorizationStatusNotDetermined -> PermissionState.NotDetermined
            UNAuthorizationStatusDenied -> PermissionState.DeniedAlways
            else -> error("unknown push authorization status $status")
        }
    }
}
