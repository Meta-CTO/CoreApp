package com.metacto.core.permissions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.metacto.core.permissions.helpers.enums.Permission
import com.metacto.core.permissions.helpers.enums.PermissionState
import com.metacto.core.permissions.helpers.exceptions.DeniedAlwaysException
import com.metacto.core.permissions.helpers.ResolverFragment
import com.metacto.core.permissions.helpers.toPlatformPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.suspendCoroutine

@Suppress("TooManyFunctions")
class PermissionManager(
    private val applicationContext: Context
) : IPermissionManager {

    private val fragmentManagerHolder = MutableStateFlow<FragmentManager?>(null)
    private val mutex = Mutex()

    override suspend fun requestPermission(permission: Permission) {
        mutex.withLock {
            val fragmentManager: FragmentManager = awaitFragmentManager()
            val resolverFragment: ResolverFragment = getOrCreateResolverFragment(fragmentManager)

            val platformPermission = permission.toPlatformPermission()
            suspendCoroutine { continuation ->
                resolverFragment.requestPermission(
                    permission,
                    platformPermission
                ) {
                    continuation.resumeWith(it)
                }
            }
        }
    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean {
        return getPermissionState(permission) == PermissionState.Granted
    }

    @Suppress("ReturnCount")
    override suspend fun getPermissionState(permission: Permission): PermissionState {
        if (permission == Permission.REMOTE_NOTIFICATION &&
            Build.VERSION.SDK_INT in VERSIONS_WITHOUT_NOTIFICATION_PERMISSION
        ) {
            val isNotificationsEnabled = NotificationManagerCompat
                .from(applicationContext)
                .areNotificationsEnabled()
            return if (isNotificationsEnabled) {
                PermissionState.Granted
            } else {
                PermissionState.DeniedAlways
            }
        }
        val permissions = permission.toPlatformPermission()
        val status = permissions.map {
            ContextCompat.checkSelfPermission(applicationContext, it)
        }
        val isAllGranted = status.all { it == PackageManager.PERMISSION_GRANTED }
        if (isAllGranted) return PermissionState.Granted

        val fragmentManager = awaitFragmentManager()
        val resolverFragment = getOrCreateResolverFragment(fragmentManager)

        val isAllRequestRationale: Boolean = permissions.all {
            !resolverFragment.shouldShowRequestPermissionRationale(it)
        }
        return if (isAllRequestRationale) PermissionState.NotDetermined
        else PermissionState.Denied
    }

    override fun openAppSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", applicationContext.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        applicationContext.startActivity(intent)
    }

    override suspend fun grantPermission(permission: Permission) {
        // Check permission state
        when (getPermissionState(permission)) {
            PermissionState.NotDetermined -> try {
                requestPermission(permission)
            } catch (_: DeniedAlwaysException) {
                withContext(Dispatchers.Main) {
                    openAppSettings()
                }
            }

            PermissionState.DeniedAlways -> withContext(Dispatchers.Main) {
                openAppSettings()
            }

            else -> requestPermission(permission)
        }
    }

    override fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager) {
        this.fragmentManagerHolder.value = fragmentManager

        val observer = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    this@PermissionManager.fragmentManagerHolder.value = null
                    source.lifecycle.removeObserver(this)
                }
            }
        }
        lifecycle.addObserver(observer)
    }

    private suspend fun awaitFragmentManager(): FragmentManager {
        val fragmentManager: FragmentManager? = fragmentManagerHolder.value
        if (fragmentManager != null) return fragmentManager

        return withTimeoutOrNull(AWAIT_FRAGMENT_MANAGER_TIMEOUT_DURATION_MS) {
            fragmentManagerHolder.filterNotNull().first()
        } ?: error(
            "fragmentManager is null, `bind` function was never called," +
                    " consider calling BindEffect(permissionManager) in the composable function"
        )
    }

    private fun getOrCreateResolverFragment(fragmentManager: FragmentManager): ResolverFragment {
        val currentFragment: Fragment? = fragmentManager.findFragmentByTag(RESOLVER_FRAGMENT_TAG)
        return if (currentFragment != null) {
            currentFragment as ResolverFragment
        } else {
            ResolverFragment().also { fragment ->
                fragmentManager
                    .beginTransaction()
                    .add(fragment, RESOLVER_FRAGMENT_TAG)
                    .commit()
            }
        }
    }

    private companion object {
        private val VERSIONS_WITHOUT_NOTIFICATION_PERMISSION =
            Build.VERSION_CODES.KITKAT until Build.VERSION_CODES.TIRAMISU
        private const val AWAIT_FRAGMENT_MANAGER_TIMEOUT_DURATION_MS = 2000L
        private const val RESOLVER_FRAGMENT_TAG = "PermissionManagerResolver"
    }
}
