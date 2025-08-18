package com.metacto.playground.presentation.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.metacto.core.ui.components.containers.ScreenColumn
import com.metacto.core.ui.navigation.NavManager
import com.metacto.playground.presentation.main.MainContract.Event
import com.metacto.playground.presentation.main.MainContract.State
import org.koin.compose.koinInject

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
        Text(
            text = "Playground app"
        )
    }
}