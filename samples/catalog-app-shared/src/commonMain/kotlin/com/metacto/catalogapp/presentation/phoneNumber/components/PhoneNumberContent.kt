package com.metacto.catalogapp.presentation.phoneNumber.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberContract.Event
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.inputFields.PrimaryTextInputField
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun PhoneNumberContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Container column
    AppScreenColumn(
        title = "Phone Number",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {

        // Phone number
        PrimaryTextInputField(
            text = state.phoneNumber,
            label = "Phone Number",
            onValueChange = {
                onEvent(Event.OnPhoneNumberChanged(it))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // County code
        PrimaryTextInputField(
            text = state.countryCode,
            label = "Country Code",
            onValueChange = {
                onEvent(Event.OnCountryCodeChanged(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing24)
        )

        // Check is valid phone number
        PrimaryFilledButton(
            text = "Check is valid phone number",
            onClick = {
                onEvent(Event.ValidatePhoneNumber)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing32)
        )

        // Get valid phone number
        PrimaryFilledButton(
            text = "Get valid phone number",
            onClick = {
                onEvent(Event.RequestValidPhoneNumber)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Get formatted phone number
        PrimaryFilledButton(
            text = "Get Formatted Phone Number",
            onClick = {
                onEvent(Event.RequestFormattedPhoneNumber)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Get E164 formatted phone number
        PrimaryFilledButton(
            text = "Get E164 Formatted Phone Number",
            onClick = {
                onEvent(Event.RequestE164PhoneNumber)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )
    }
}
