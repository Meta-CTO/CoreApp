package com.metacto.core.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentActivity

@Suppress("FunctionNaming")
@Composable
actual fun BindEffect(permissionManager: IPermissionManager) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(permissionManager, lifecycleOwner, context) {
        val fragmentManager = (context as FragmentActivity).supportFragmentManager
        permissionManager.bind(lifecycleOwner.lifecycle, fragmentManager)
    }
}
