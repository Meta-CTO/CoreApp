package com.metacto.catalogapp.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.customDialog.CustomDialogSamplesScreen
import com.metacto.catalogapp.presentation.dateConverter.DateConverterScreen
import com.metacto.catalogapp.presentation.datePicker.DatePickerScreen
import com.metacto.catalogapp.presentation.files.FilesSamplesScreen
import com.metacto.catalogapp.presentation.imagePicker.ImagePickerSamplesScreen
import com.metacto.catalogapp.presentation.imagePreloader.ImagePreloaderScreen
import com.metacto.catalogapp.presentation.lottie.LottieScreen
import com.metacto.catalogapp.presentation.main.MainContract.Event
import com.metacto.catalogapp.presentation.main.MainContract.State
import com.metacto.catalogapp.presentation.main.applepay.ApplePayScreen
import com.metacto.catalogapp.presentation.mediaPicker.MediaPickerScreen
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerScreen
import com.metacto.catalogapp.presentation.navManager.NavManagerScreen
import com.metacto.catalogapp.presentation.notifications.NotificationsSamplesScreen
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberScreen
import com.metacto.catalogapp.presentation.sheetSamples.SheetSamplesScreen
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject
import sp.bvantur.inspektify.ktor.InspektifyKtor

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
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Image picker samples",
            onClick = {
                navManage.navigate(ImagePickerSamplesScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Media picker samples",
            onClick = {
                navManage.navigate(MediaPickerScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Image Preloader sample",
            onClick = {
                navManage.navigate(ImagePreloaderScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Media Manager samples",
            onClick = {
                navManage.navigate(MediaManagerScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Date Picker samples",
            onClick = {
                navManage.navigate(DatePickerScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Date Converter samples",
            onClick = {
                navManage.navigate(DateConverterScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Phone Number samples",
            onClick = {
                navManage.navigate(PhoneNumberScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Lottie samples",
            onClick = {
                navManage.navigate(LottieScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "NavManager samples",
            onClick = {
                navManage.navigate(NavManagerScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Sheet samples",
            onClick = {
                navManage.navigate(SheetSamplesScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Open Inspektify",
            onClick = {
                InspektifyKtor.startInspektify()
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Test Crash",
            onClick = {
                throw RuntimeException("Test crash for Firebase Crashlytics")
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Apple Pay samples",
            onClick = {
                navManage.navigate(ApplePayScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )

        PrimaryFilledButton(
            text = "Custom dialog samples",
            onClick = {
                navManage.navigate(CustomDialogSamplesScreen())
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}