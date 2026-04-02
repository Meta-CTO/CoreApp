package com.sampleApp.app.presentation.permissions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.metacto.core.permissions.enums.Permission
import com.metacto.core.permissions.enums.PermissionState
import com.metacto.core.presentation.components.buttons.PrimaryFilledButton
import com.sampleApp.app.presentation.permissions.models.PermissionDetailData
import com.sampleApp.app.presentation.permissions.models.PermissionDetailsProvider
import com.sampleApp.app.presentation.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PermissionsTestContent(
    permissionStates: Map<Permission, PermissionState>,
    isLoading: Boolean,
    onBackClicked: () -> Unit,
    onRequestPermission: (Permission, Boolean) -> Unit,
) {
    val permissionDetails = remember { PermissionDetailsProvider.getAllPermissionDetails() }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Permissions Test") },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppTheme.spacings.spacing16),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.spacing12)
        ) {

            items(permissionDetails) { permissionDetail ->
                SimplePermissionCard(
                    permissionDetail = permissionDetail,
                    currentState = permissionStates[permissionDetail.permission] ?: PermissionState.NotDetermined,
                    isLoading = isLoading ,
                    onRequestPermission = {
                        onRequestPermission(permissionDetail.permission, true)
                    }
                )
            }
        }
    }
}

@Composable
private fun SimplePermissionCard(
    permissionDetail: PermissionDetailData,
    currentState: PermissionState,
    isLoading: Boolean,
    onRequestPermission: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppTheme.spacings.spacing4)
    ) {
        Column(modifier = Modifier.padding(AppTheme.spacings.spacing16)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = permissionDetail.displayName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                PermissionStateChip(currentState)
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(AppTheme.spacings.spacing8))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(AppTheme.spacings.spacing12))

            PrimaryFilledButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Request Permission",
                onClick = onRequestPermission
            )
        }
    }
}

@Composable
private fun PermissionStateChip(state: PermissionState) {
    val (color, icon, text) = when (state) {
        PermissionState.Granted -> Triple(AppTheme.colors.terra, Icons.Default.Check, "Granted")
        PermissionState.Denied -> Triple(AppTheme.colors.nectarineDark, Icons.Default.Warning, "Denied")
        PermissionState.DeniedAlways -> Triple(AppTheme.colors.terra, Icons.Default.Close, "Denied Always")
        PermissionState.NotDetermined -> Triple(AppTheme.colors.gray, Icons.Default.Help, "Unknown")
    }

    Row(
        modifier = Modifier
            .background(color = color.copy(alpha = 0.1f), shape = AppTheme.shapes.roundedCorner12)
            .padding(horizontal = AppTheme.spacings.spacing8, vertical = AppTheme.spacings.spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacings.spacing4)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(AppTheme.spacings.spacing16)
        )
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}