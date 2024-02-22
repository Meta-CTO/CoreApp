package com.metacto.core.permissions

import androidx.compose.runtime.Composable

@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionManager: IPermissionManager)
