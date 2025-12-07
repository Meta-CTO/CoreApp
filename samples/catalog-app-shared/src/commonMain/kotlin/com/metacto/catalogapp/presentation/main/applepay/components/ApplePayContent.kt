package com.metacto.catalogapp.presentation.main.applepay.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.main.applepay.ApplePayContract.Event
import com.metacto.catalogapp.presentation.main.applepay.ApplePayContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import com.metacto.core.ui.navigation.NavManager
import com.metacto.core.ui.theme.CoreTheme
import org.koin.compose.koinInject

@Composable
internal fun ApplePayContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val globalState = koinInject<IAppGlobalState>()

    // Container column
    AppScreenColumn(
        title = "ApplePay",
        verticalArrangement = Arrangement.spacedBy(spacings.spacing16),
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        PrimaryFilledButton(
            text = "Normal Button",
            modifier = Modifier.fillMaxWidth()
        )

        ApplePayButton(
            onClick = {
                globalState.showSuccess("Apple Pay button clicked")
            },
            modifier = Modifier
                .width(320.dp)
                .height(CoreTheme.spacings.primaryFilledButton.minHeightNormal)
                .align(Alignment.CenterHorizontally)
        )
    }
}
