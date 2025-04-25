package com.metacto.kmm.permissions

import androidx.compose.runtime.Composable


@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionManager: IPermissionManager)
