package com.metacto.core.ui.permissions

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner

@Suppress("FunctionNaming")
@Composable
actual fun BindEffect(permissionManager: IPermissionManager) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(permissionManager, lifecycleOwner, context) {
        val activity = checkNotNull(context as? ComponentActivity) {
            "$context context is not instance of ComponentActivity"
        }

        permissionManager.bind(activity)
    }
}
