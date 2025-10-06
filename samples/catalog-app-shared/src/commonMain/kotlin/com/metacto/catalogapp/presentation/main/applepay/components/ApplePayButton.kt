package com.metacto.catalogapp.presentation.main.applepay.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal expect fun ApplePayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
)