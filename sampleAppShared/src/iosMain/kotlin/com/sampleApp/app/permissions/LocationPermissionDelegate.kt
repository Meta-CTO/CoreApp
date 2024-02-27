package com.sampleApp.app.permissions

import com.metacto.core.permissions.helpers.enums.Permission
import com.metacto.core.permissions.helpers.enums.PermissionState
import com.metacto.core.permissions.helpers.exceptions.DeniedAlwaysException
import com.metacto.core.permissions.helpers.PermissionDelegate
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class LocationPermissionDelegate(
    private val locationManagerDelegate: LocationManagerDelegate,
    private val permission: Permission
) : PermissionDelegate {
    override suspend fun providePermission() {
        return provideLocationPermission(CLLocationManager.authorizationStatus())
    }

    override suspend fun getPermissionState(): PermissionState {
        return when (val status = CLLocationManager.authorizationStatus()) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> PermissionState.Granted

            kCLAuthorizationStatusNotDetermined -> PermissionState.NotDetermined
            kCLAuthorizationStatusDenied -> PermissionState.DeniedAlways
            else -> error("unknown location authorization status $status")
        }
    }

    private suspend fun provideLocationPermission(
        status: CLAuthorizationStatus
    ) {
        when (status) {
            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse -> return

            kCLAuthorizationStatusNotDetermined -> {
                val newStatus = suspendCoroutine<CLAuthorizationStatus> { continuation ->
                    locationManagerDelegate.requestLocationAccess { continuation.resume(it) }
                }
                provideLocationPermission(newStatus)
            }

            kCLAuthorizationStatusDenied -> throw DeniedAlwaysException(permission)
            else -> error("unknown location authorization status $status")
        }
    }
}
