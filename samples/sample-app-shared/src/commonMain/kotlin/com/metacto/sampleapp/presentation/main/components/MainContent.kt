package com.metacto.sampleapp.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.sampleapp.presentation.main.MainContract.Event
import com.metacto.sampleapp.presentation.main.MainContract.State

@Composable
internal fun MainContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hello MetaCTO!"
        )
    }
}