package com.sampleApp.app.presentation.app.app

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.metacto.core.presentation.app.CoreAppContent
import com.metacto.core.navigation.NavManager
import com.sampleApp.app.presentation.app.globalState.IAppGlobalState
import com.sampleApp.app.presentation.app.globalState.models.AppBackgroundType
import com.sampleApp.app.presentation.main.MainScreen
import com.sampleApp.app.presentation.theme.AppTheme
import com.sampleApp.app.presentation.theme.AppThemeContent
import org.koin.compose.rememberKoinInject

@Composable
internal fun AppContent() {
    // Get main objects
    val globalState = rememberKoinInject<IAppGlobalState>()
    val navManager = rememberKoinInject<NavManager>()

    // Get states
    val backgroundType by globalState.appBgState
    val navigateToLogin by globalState.navigateToLogin

    // Prepare app background
    val bgColor = when(backgroundType) {
        AppBackgroundType.PRIMARY -> AppTheme.colors.miniPeach
        AppBackgroundType.SECONDARY -> AppTheme.colors.lilac
    }

    // Render core app content
    AppThemeContent {
        CoreAppContent(
            modifier = Modifier.background(bgColor),
            globalState = globalState,
            navManager = navManager,
            startScreen = MainScreen()
        )
    }
}
