package com.metacto.catalogapp.presentation.app.app

import androidx.compose.runtime.Composable
import com.metacto.core.ui.app.CoreAppContent
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.app.globalState.IAppGlobalState
import com.metacto.catalogapp.presentation.main.MainScreen
import com.metacto.catalogapp.presentation.theme.AppThemeContent
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
