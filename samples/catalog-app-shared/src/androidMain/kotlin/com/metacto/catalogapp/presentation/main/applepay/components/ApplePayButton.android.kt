package com.metacto.catalogapp.presentation.main.applepay.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal actual fun ApplePayButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Text(
        text = "Apple Pay is not available on Android",
        modifier = modifier
    )
}