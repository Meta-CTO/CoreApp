package com.metacto.catalogapp.presentation.permissions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.permissions.PermissionsContract.Event
import com.metacto.catalogapp.presentation.permissions.PermissionsContract.State
import com.metacto.catalogapp.presentation.theme.colors
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.catalogapp.presentation.theme.typography
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.globalState.ICoreGlobalState
import com.metacto.core.ui.globalState.models.SnackBarParams
import com.metacto.core.ui.globalState.models.SnackBarType
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.ui.permissions.IPermissionManager
import com.metacto.core.ui.permissions.enums.Permission
import com.metacto.core.ui.permissions.enums.PermissionState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
internal fun PermissionsContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val permissionManager = koinInject<IPermissionManager>()
    val coreGlobalState = koinInject<ICoreGlobalState>()


    // State
    val coroutine = rememberCoroutineScope()

    // Container column
    AppScreenColumn(
        title = "Permissions",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
        verticalArrangement = Arrangement.spacedBy(spacings.spacing16)
    ) {
        Permission.entries.map { permission ->
            Column(
                verticalArrangement = Arrangement.spacedBy(spacings.spacing8)
            ) {
                Text(
                    permission.name,
                    style = typography.primary.bold._16,
                    color = colors.black,
                )

                PrimaryFilledButton(
                    text = "request ${permission.name.lowercase()}",
                    onClick = {
                        coroutine.launch {
                            permissionManager.requestPermission(permission)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()

                )

                PrimaryFilledButton(
                    text = "is ${permission.name.lowercase()} granted",
                    onClick = {
                        coroutine.launch {
                            val isGranted = permissionManager.isPermissionGranted(permission)
                            if (isGranted) {
                                coreGlobalState.snackBar(
                                    SnackBarParams(
                                        message = "${permission.name.lowercase()} is granted",
                                        type = SnackBarType.SUCCESS,
                                    )
                                )
                            } else {
                                coreGlobalState.snackBar(
                                    SnackBarParams(
                                        message = "${permission.name.lowercase()} is not granted",
                                        type = SnackBarType.ERROR,
                                    )
                                )
                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                PrimaryFilledButton(
                    text = "get ${permission.name.lowercase()} state",
                    onClick = {
                        coroutine.launch {
                            val status = permissionManager.getPermissionState(permission)
                            if(status == PermissionState.Granted) {
                                coreGlobalState.snackBar(
                                    SnackBarParams(
                                        message = "${permission.name.lowercase()} state is $status",
                                        type = SnackBarType.SUCCESS,
                                    )
                                )
                            } else {
                                coreGlobalState.snackBar(
                                    SnackBarParams(
                                        message = "${permission.name.lowercase()} state is $status",
                                        type = SnackBarType.ERROR,
                                    )
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}
