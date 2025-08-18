package com.metacto.catalogapp.presentation.navManager.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import org.koin.compose.koinInject
import com.metacto.catalogapp.presentation.navManager.NavManagerContract.State
import com.metacto.catalogapp.presentation.navManager.NavManagerContract.Event
import com.metacto.core.ui.components.buttons.PrimaryFilledButton
import kotlinx.coroutines.launch

@Composable
internal fun NavManagerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val globalState = koinInject<IAppGlobalState>()
    val coroutineScope = rememberCoroutineScope()

    // Container column
    AppScreenColumn(
        title = "NavManager",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        PrimaryFilledButton(
            text = "Get last screen",
            onClick = {
                coroutineScope.launch {
                    try {
                        val lastScreen = navManager.getLastScreen()
                        if (lastScreen != null) {
                            globalState.showSuccess("Last screen: ${lastScreen::class.simpleName}")
                        }
                    } catch (e: Throwable) {
                        globalState.showError(e.message.orEmpty())
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
