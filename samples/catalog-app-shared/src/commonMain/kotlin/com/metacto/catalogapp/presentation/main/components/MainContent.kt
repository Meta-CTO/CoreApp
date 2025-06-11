package com.metacto.catalogapp.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.files.FilesSamplesScreen
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesScreen
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderScreen
import com.metacto.catalogapp.presentation.main.MainContract.Event
import com.metacto.catalogapp.presentation.main.MainContract.State
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerScreen
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesScreen
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberScreen
import com.metacto.catalogapp.presentation.permissions.PermissionsScreen
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun MainContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManage = koinInject<NavManager>()

    ScreenColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacings.spacing16),
        isScrollable = true,
        modifier = Modifier.fillMaxSize()
    ) {
        PrimaryFilledButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Notification samples",
            onClick = {
                navManage.navigate(NotificationsSamplesScreen())
            }
        )

        PrimaryFilledButton(
            text = "File samples",
            onClick = {
                navManage.navigate(FilesSamplesScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),
        )

        PrimaryFilledButton(
            text = "Image picker samples",
            onClick = {
                navManage.navigate(ImagePickerSamplesScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),
        )

        PrimaryFilledButton(
            text = "Image Preloader sample",
            onClick = {
                navManage.navigate(ImagePreloaderScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),
        )

        PrimaryFilledButton(
            text = "Media Manager samples",
            onClick = {
                navManage.navigate(MediaManagerScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),

            )

        PrimaryFilledButton(
            text = "Permissions samples",
            onClick = {
                navManage.navigate(PermissionsScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),

            )

        // Phone number samples
        PrimaryFilledButton(
            text = "Phone Number samples",
            onClick = {
                navManage.navigate(PhoneNumberScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),

            )
    }
}