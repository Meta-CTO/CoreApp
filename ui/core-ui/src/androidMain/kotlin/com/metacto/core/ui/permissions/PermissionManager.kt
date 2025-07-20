package com.metacto.core.ui.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.metacto.core.extensions.openAppSettings
import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.enums.PermissionState
import com.metacto.core.ui.permissions.exceptions.DeniedAlwaysException
import com.metacto.core.ui.permissions.helpers.toPlatformPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import java.util.UUID
import kotlin.coroutines.suspendCoroutine
import kotlin.math.max

/**
 * Android Implementation of Permission Manager
 * 
 * This class handles the complex permission management for Android, with special handling
 * for different Android versions and permission types. It addresses several Android-specific
 * challenges:
 * 
 * ## Key Challenges Solved:
 * 
 * ### 1. Android 13+ Notification Permissions
 * - POST_NOTIFICATIONS permission introduced in API 33
 * - Apps must target API 33+ or permission is auto-denied without dialog
 * - Solution: Proper targetSdk configuration allows standard Android APIs to work
 * 
 * ### 2. Race Conditions
 * - Permission requests must happen after Activity is bound
 * - BindEffect and permission requests can race during screen composition
 * - Solution: Proper activity lifecycle management and mutex protection
 * 
 * ### 3. Permission State Tracking
 * - Android provides shouldShowRequestPermissionRationale() for state management
 * - With proper targetSdk configuration, standard Android APIs work reliably
 * - Solution: Use Android's built-in permission state logic
 * 
 * ### 4. Activity Lifecycle Management
 * - Permission launchers tied to Activity lifecycle
 * - Activity can be destroyed during permission flow
 * - Solution: StateFlow-based activity holder with lifecycle observers
 * 
 * ## Implementation Details:
 * 
 * ### Permission Request Flow:
 * 1. Check if permission already granted → return early
 * 2. Get ActivityResultLauncher from bound activity
 * 3. Map Permission enum to Android permission strings
 * 4. Launch permission request via ActivityResultContracts
 * 5. Handle result in callback with proper exception mapping
 * 
 * ### Notification Permission Special Handling:
 * - Uses standard shouldShowRequestPermissionRationale for state management
 * - Considers system notification settings for older Android versions
 * - Relies on Android's built-in permission logic with proper targetSdk
 * 
 * ### Error Handling:
 * - DeniedException: User denied, can ask again
 * - DeniedAlwaysException: Permanently denied, redirect to settings
 * - RequestCanceledException: Dialog dismissed without selection
 * - IllegalStateException: Activity not bound or other setup issues
 * 
 * @param context Application context for permission checks
 * 
 * @see IPermissionManager for interface documentation
 * @see BindEffect for proper activity binding in Compose
 */
internal class PermissionManager(private val context: Context) : IPermissionManager {
    private val activityHolder = MutableStateFlow<Activity?>(null)
    private val launcherHolder = MutableStateFlow<ActivityResultLauncher<Array<String>>?>(null)
    private var permissionCallback: PermissionCallback? = null
    private val mutex = Mutex()
    private val key = UUID.randomUUID().toString()

    /**
     * Binds the PermissionManager to a ComponentActivity.
     * 
     * This method MUST be called before requesting any permissions. It:
     * 1. Stores the activity reference for permission requests
     * 2. Sets up the ActivityResultLauncher for handling permission dialogs
     * 3. Registers lifecycle observers to clean up when activity is destroyed
     * 
     * ⚠️ Critical: This must be called from the main thread and before any permission requests.
     * 
     * @param activity The ComponentActivity to bind to
     */
    override fun bind(activity: ComponentActivity) {
        activityHolder.value = activity
        setupPermissionLauncher(activity)
        setupLifecycleObserver(activity)
    }

    override suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    ) {
        // Check if permission is already granted
        if (isPermissionGranted(permission)) {
            return // Permission already granted, no need to request
        }
        
        if (openAppSettingsIfRequired) {
            handlePermissionRequestWithSettings(permission)
        } else {
            handlePermissionRequest(permission)
        }
    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean =
        getPermissionState(permission) == PermissionState.Granted

    override suspend fun getPermissionState(permission: Permission): PermissionState {
        return when {
            isNotificationPermission(permission) -> getNotificationPermissionState()
            else -> getRuntimePermissionState(permission)
        }
    }

    private fun setupPermissionLauncher(activity: ComponentActivity) {
        val registry = (activity as ActivityResultRegistryOwner).activityResultRegistry
        val launcher = registry.register(
            key,
            ActivityResultContracts.RequestMultiplePermissions(),
            ::handlePermissionResult
        )
        launcherHolder.value = launcher
    }

    private fun setupLifecycleObserver(activity: ComponentActivity) {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                activityHolder.value = null
                launcherHolder.value = null
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    private fun handlePermissionResult(permissions: Map<String, Boolean>) {
        val callback = permissionCallback ?: return

        when {
            permissions.isEmpty() -> {
                callback.callback(Result.failure(
                    com.metacto.core.ui.permissions.exceptions.RequestCanceledException(
                        callback.permission
                    )
                ))
            }
            permissions.values.all { it } -> {
                callback.callback(Result.success(Unit))
            }
            else -> {
                handlePermissionDenial(callback, permissions.keys.first())
            }
        }
    }

    private fun handlePermissionDenial(callback: PermissionCallback, permission: String) {
        val activity = activityHolder.value ?: return
        
        // Use standard Android permission logic for all permissions
        // Now that targetSdk is correctly set, shouldShowRequestPermissionRationale should work properly
        val exception = if (shouldShowRequestPermissionRationale(activity, permission)) {
            // User denied permission but we can ask again
            com.metacto.core.ui.permissions.exceptions.DeniedException(callback.permission)
        } else {
            // User permanently denied permission or this is first request
            // Let Android handle the logic through its standard APIs
            DeniedAlwaysException(callback.permission)
        }
        callback.callback(Result.failure(exception))
    }

    private suspend fun handlePermissionRequest(permission: Permission) {
        mutex.withLock {
            val launcher = awaitActivityResultLauncher()
            val platformPermissions = permission.toPlatformPermission()

            suspendCoroutine { continuation ->
                permissionCallback = PermissionCallback(permission, continuation::resumeWith)
                launcher.launch(platformPermissions.toTypedArray())
            }
        }
    }

    private suspend fun handlePermissionRequestWithSettings(permission: Permission) {
        val initialState = getPermissionState(permission)
        try {
            handlePermissionRequest(permission)
        } catch (exception: DeniedAlwaysException) {
            if (initialState == PermissionState.Denied) {
                context.openAppSettings()
            }
            throw exception
        }
    }

    private suspend fun awaitActivityResultLauncher(): ActivityResultLauncher<Array<String>> {
        return launcherHolder.value ?: withTimeoutOrNull(AWAIT_ACTIVITY_TIMEOUT_MS) {
            launcherHolder.filterNotNull().first()
        } ?: throw IllegalStateException(getBindErrorMessage())
    }


    private fun isNotificationPermission(permission: Permission): Boolean =
        permission == Permission.REMOTE_NOTIFICATION &&
                Build.VERSION.SDK_INT > VERSIONS_WITHOUT_NOTIFICATION_PERMISSION.max()

    private fun getNotificationPermissionState(): PermissionState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionStatus = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            
            when (permissionStatus) {
                PackageManager.PERMISSION_GRANTED -> PermissionState.Granted
                PackageManager.PERMISSION_DENIED -> {
                    // For Android 13+, we can rely on standard shouldShowRequestPermissionRationale
                    // now that targetSdk is properly configured
                    val activity = activityHolder.value
                    if (activity != null && shouldShowRequestPermissionRationale(activity, Manifest.permission.POST_NOTIFICATIONS)) {
                        PermissionState.Denied // User denied but can ask again
                    } else {
                        PermissionState.NotDetermined // Never asked or permanently denied
                    }
                }
                else -> PermissionState.NotDetermined
            }
        } else {
            // For older Android versions, check system notification settings
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                PermissionState.Granted
            } else {
                PermissionState.Denied
            }
        }
    }

    private fun getRuntimePermissionState(permission: Permission): PermissionState {
        val permissions = permission.toPlatformPermission()
        val status = permissions.map {
            ContextCompat.checkSelfPermission(context, it)
        }

        return when {
            status.all { it == PackageManager.PERMISSION_GRANTED } -> PermissionState.Granted
            else -> {
                val activity = activityHolder.value
                if (activity != null) {
                    val shouldShowRationale = permissions.any { 
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
                    }
                    
                    if (shouldShowRationale) {
                        // User has denied the permission but can ask again
                        PermissionState.Denied
                    } else {
                        // Permission never asked or permanently denied
                        // Now that targetSdk is properly set, we can rely on Android's standard logic
                        PermissionState.NotDetermined
                    }
                } else {
                    // If no activity available, assume permission can be requested
                    PermissionState.NotDetermined
                }
            }
        }
    }


    private fun shouldShowRequestPermissionRationale(
        activity: Activity,
        permission: String
    ): Boolean = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

    private fun getBindErrorMessage() = """
        Activity/Launcher is null. 'bind' function was never called.
        Please call permissionsController.bind(activity) or use
        BindEffect(permissionsController) in your composable function.
        For more information, visit:
        https://github.com/icerockdev/moko-permissions/blob/master/README.md
    """.trimIndent()

    private companion object {
        private const val AWAIT_ACTIVITY_TIMEOUT_MS = 2000L
        private val VERSIONS_WITHOUT_NOTIFICATION_PERMISSION =
            Build.VERSION_CODES.KITKAT until Build.VERSION_CODES.TIRAMISU
    }
}

private data class PermissionCallback(
    val permission: Permission,
    val callback: (Result<Unit>) -> Unit
)