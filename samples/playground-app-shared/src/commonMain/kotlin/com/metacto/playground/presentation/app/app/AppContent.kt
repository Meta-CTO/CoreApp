package com.metacto.playground.presentation.app.app

import androidx.compose.runtime.Composable
import com.metacto.core.ui.app.CoreAppContent
import com.metacto.core.ui.navigation.NavManager
import com.metacto.playground.presentation.app.globalState.IAppGlobalState
import com.metacto.playground.presentation.main.MainScreen
import com.metacto.playground.presentation.theme.AppThemeContent
import org.koin.compose.koinInject

@Composable
internal fun AppContent() {
    // Get main objects
    val globalState = koinInject<IAppGlobalState>()
    val navManager = koinInject<NavManager>()

    // Render core app content
    AppThemeContent {
        CoreAppContent(
            globalState = globalState,
            navManager = navManager,
            startScreen = MainScreen()
        )
    }
}
