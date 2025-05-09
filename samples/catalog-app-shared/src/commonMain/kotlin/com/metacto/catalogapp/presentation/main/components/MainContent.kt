package com.metacto.catalogapp.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.main.MainContract.Event
import com.metacto.catalogapp.presentation.main.MainContract.State
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesScreen
import org.koin.compose.koinInject
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerScreen
import com.metacto.catalogapp.presentation.theme.spacings

@Composable
internal fun MainContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManage = koinInject<NavManager>()

    ScreenColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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
            text = "ImagePicker",
            onClick = {
                navManage.navigate(ImagePickerScreen())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16),

            )
    }
}