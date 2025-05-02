package com.metacto.catalogapp.permissions

import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.enums.PermissionState
import com.metacto.core.ui.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.ui.permissions.exceptions.DeniedException
import com.metacto.core.ui.permissions.helpers.PermissionDelegate
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreBluetooth.CBCentralManager
import platform.CoreBluetooth.CBCentralManagerDelegateProtocol
import platform.CoreBluetooth.CBManager
import platform.CoreBluetooth.CBManagerAuthorizationAllowedAlways
import platform.CoreBluetooth.CBManagerAuthorizationDenied
import platform.CoreBluetooth.CBManagerAuthorizationNotDetermined
import platform.CoreBluetooth.CBManagerAuthorizationRestricted
import platform.CoreBluetooth.CBManagerState
import platform.CoreBluetooth.CBManagerStatePoweredOff
import platform.CoreBluetooth.CBManagerStatePoweredOn
import platform.CoreBluetooth.CBManagerStateResetting
import platform.CoreBluetooth.CBManagerStateUnauthorized
import platform.CoreBluetooth.CBManagerStateUnknown
import platform.CoreBluetooth.CBManagerStateUnsupported
import platform.Foundation.NSSelectorFromString
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class BluetoothPermissionDelegate(
    private val permission: Permission
) : PermissionDelegate {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun providePermission() {
        val isNotDetermined: Boolean =
            if (CBManager.resolveClassMethod(NSSelectorFromString("authorization"))) {
                CBManager.authorization == CBManagerAuthorizationNotDetermined
            } else {
                CBCentralManager().state == CBManagerStateUnknown
            }

        val state: CBManagerState = if (isNotDetermined) {
            suspendCoroutine { continuation ->
                CBCentralManager(object : NSObject(), CBCentralManagerDelegateProtocol {
                    override fun centralManagerDidUpdateState(central: CBCentralManager) {
                        continuation.resume(central.state)
                    }
                }, null)
            }
        } else {
            CBCentralManager().state
        }

        when (state) {
            CBManagerStatePoweredOn -> return
            CBManagerStateUnauthorized -> throw DeniedAlwaysException(permission)
            CBManagerStatePoweredOff ->
                throw DeniedException(permission, "Bluetooth is powered off")

            CBManagerStateResetting ->
                throw DeniedException(permission, "Bluetooth is restarting")

            CBManagerStateUnsupported ->
                throw DeniedAlwaysException(permission, "Bluetooth is not supported on this device")

            CBManagerStateUnknown ->
                error("Bluetooth state should be known at this point")

            else ->
                error("Unknown state (Permissions library should be updated) : $state")
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getPermissionState(): PermissionState {
        if (CBManager.resolveClassMethod(NSSelectorFromString("authorization"))) {
            return when (val state = CBManager.authorization) {
                CBManagerAuthorizationNotDetermined -> PermissionState.NotDetermined
                CBManagerAuthorizationAllowedAlways, CBManagerAuthorizationRestricted -> PermissionState.Granted
                CBManagerAuthorizationDenied -> PermissionState.DeniedAlways
                else -> error("unknown state $state")
            }
        }
        return when (val state = CBCentralManager().state) {
            CBManagerStatePoweredOn -> PermissionState.Granted
            CBManagerStateUnauthorized, CBManagerStatePoweredOff,
            CBManagerStateResetting, CBManagerStateUnsupported -> PermissionState.DeniedAlways

            CBManagerStateUnknown -> PermissionState.NotDetermined
            else -> error("unknown state $state")
        }
    }
}
