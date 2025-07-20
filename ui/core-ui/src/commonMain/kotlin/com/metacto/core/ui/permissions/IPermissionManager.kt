package com.metacto.core.ui.permissions

import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.enums.PermissionState

/**
 * Permission Manager Interface
 * 
 * Provides a unified API for requesting and managing permissions across platforms.
 * This interface handles the complexity of different permission systems between Android and iOS.
 * 
 * Key Features:
 * - Cross-platform permission management
 * - Automatic permission state tracking
 * - Smart handling of Android 13+ notification permissions
 * - Race condition prevention through proper activity binding
 * 
 * Important Notes:
 * - For Android: Requires app to target API 33+ for notification permissions to work
 * - Must call bind() with Activity before requesting permissions
 * - Uses BindEffect in Compose to handle activity lifecycle automatically
 * 
 * Usage Example:
 * ```kotlin
 * // In Compose screen
 * val permissionManager = koinInject<IPermissionManager>()
 * BindEffect(permissionManager) // Binds to current activity
 * 
 * // Request permission
 * try {
 *     permissionManager.requestPermission(Permission.REMOTE_NOTIFICATION)
 *     // Permission granted
 * } catch (e: DeniedException) {
 *     // User denied, can ask again
 * } catch (e: DeniedAlwaysException) {
 *     // User permanently denied, redirect to settings
 * }
 * ```
 */
expect interface IPermissionManager {
    /**
     * Requests a specific permission from the user.
     * 
     * This method will:
     * 1. Check if permission is already granted (returns immediately if true)
     * 2. Show the system permission dialog
     * 3. Handle the user's response appropriately
     * 
     * @param permission The permission to request (e.g., REMOTE_NOTIFICATION, CAMERA)
     * @param openAppSettingsIfRequired If true, will automatically open app settings
     *        when permission is permanently denied (DeniedAlwaysException)
     * 
     * @throws DeniedException When user denies permission but can be asked again
     * @throws DeniedAlwaysException When user permanently denies permission
     * @throws RequestCanceledException When permission request is cancelled
     * @throws IllegalStateException When PermissionManager is not bound to activity
     */
    suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean = true
    )

    /**
     * Checks if a specific permission is currently granted.
     * 
     * @param permission The permission to check
     * @return true if permission is granted, false otherwise
     */
    suspend fun isPermissionGranted(permission: Permission): Boolean

    /**
     * Gets the current state of a permission.
     * 
     * @param permission The permission to check
     * @return PermissionState.Granted, PermissionState.Denied, or PermissionState.NotDetermined
     */
    suspend fun getPermissionState(permission: Permission): PermissionState
}
