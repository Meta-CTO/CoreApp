package com.metacto.core.ui.permissions

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
import com.metacto.core.ui.permissions.helpers.IPermissionFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import java.util.UUID
import kotlin.coroutines.suspendCoroutine

internal class PermissionManager(
    private val context: Context,
    private val permissionFactory: IPermissionFactory
) : IPermissionManager {

    private val activityHolder = MutableStateFlow<Activity?>(null)
    private val launcherHolder = MutableStateFlow<ActivityResultLauncher<Array<String>>?>(null)
    private var permissionCallback: PermissionCallback? = null
    private val mutex = Mutex()
    private val key = UUID.randomUUID().toString()

    override fun bind(activity: ComponentActivity) {
        activityHolder.value = activity
        setupPermissionLauncher(activity)
        setupLifecycleObserver(activity)
    }

    override suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    ) {
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
        val exception = if (shouldShowRequestPermissionRationale(activity, permission)) {
            com.metacto.core.ui.permissions.exceptions.DeniedException(callback.permission)
        } else {
            DeniedAlwaysException(callback.permission)
        }
        callback.callback(Result.failure(exception))
    }

    private suspend fun handlePermissionRequest(permission: Permission) {
        mutex.withLock {
            val launcher = awaitActivityResultLauncher()
            val platformPermissions = getAndroidPermissions(permission)

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

    private fun getAndroidPermissions(permission: Permission): List<String> {
        return permissionFactory.getPermissions(permission)
    }

    private suspend fun awaitActivityResultLauncher(): ActivityResultLauncher<Array<String>> {
        return launcherHolder.value ?: withTimeoutOrNull(AWAIT_ACTIVITY_TIMEOUT_MS) {
            launcherHolder.filterNotNull().first()
        } ?: throw IllegalStateException(getBindErrorMessage())
    }

    private suspend fun awaitActivity(): Activity {
        return activityHolder.value ?: withTimeoutOrNull(AWAIT_ACTIVITY_TIMEOUT_MS) {
            activityHolder.filterNotNull().first()
        } ?: throw IllegalStateException(getBindErrorMessage())
    }

    private fun isNotificationPermission(permission: Permission): Boolean =
        permission == Permission.REMOTE_NOTIFICATION &&
                Build.VERSION.SDK_INT in VERSIONS_WITHOUT_NOTIFICATION_PERMISSION

    private fun getNotificationPermissionState(): PermissionState {
        val isEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
        return if (isEnabled) PermissionState.Granted else PermissionState.DeniedAlways
    }

    private suspend fun getRuntimePermissionState(permission: Permission): PermissionState {
        val permissions = getAndroidPermissions(permission)
        val status = permissions.map {
            ContextCompat.checkSelfPermission(context, it)
        }

        return when {
            status.all { it == PackageManager.PERMISSION_GRANTED } -> PermissionState.Granted
            permissions.all { !shouldShowRequestPermissionRationale(it) } -> PermissionState.Denied
            else -> PermissionState.NotDetermined
        }
    }

    private suspend fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        shouldShowRequestPermissionRationale(awaitActivity(), permission)

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