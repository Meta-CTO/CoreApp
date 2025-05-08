package com.metacto.catalogapp.presentation.phoneNumber.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberSamplesContract.Event
import com.metacto.catalogapp.presentation.phoneNumber.PhoneNumberSamplesContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.phone.IPhoneNumberManager
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.components.inputFields.PrimaryTextInputField
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun PhoneNumberSamplesContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val phoneManager = koinInject<IPhoneNumberManager>()

    // states
    var phoneNumber by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

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

        // Status
        Text(text = status)

        // Phone number
        PrimaryTextInputField(
            text = phoneNumber,
            label = "Phone Number",
            onValueChange = {
                phoneNumber = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // County code
        PrimaryTextInputField(
            text = countryCode,
            label = "Country Code",
            onValueChange = {
                countryCode = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing24)
        )

        // Check is valid phone number
        PrimaryFilledButton(
            text = "Check is valid phone number",
            onClick = {
                status = phoneManager.isValidPhoneNumber(
                    number = phoneNumber,
                    countryCode = countryCode
                ).toString()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing32)
        )

        // Get valid phone number
        PrimaryFilledButton(
            text = "Get valid phone number",
            onClick = {
                status = phoneManager.getValidPhoneNumber(
                    number = phoneNumber,
                    countryCode = countryCode
                ).toString()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Get formatted phone number
        PrimaryFilledButton(
            text = "Get Formatted Phone Number",
            onClick = {
                status = phoneManager.getFormattedPhoneNumber(
                    number = phoneNumber,
                    countryCode = countryCode
                ).toString()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )

        // Get E164 formatted phone number
        PrimaryFilledButton(
            text = "Get E164 Formatted Phone Number",
            onClick = {
                status = phoneManager.getE164FormattedPhoneNumber(
                    number = phoneNumber,
                    countryCode = countryCode
                ).toString()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing16)
        )
    }
}
