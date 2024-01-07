package com.sampleApp.app.presentation.landing.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.components.SplashContent

internal object SplashScreen : BaseScreen<SplashViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<SplashViewModel>()

        // Notify screen appeared
        viewModel.setEvent(Event.ScreenAppeared)

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.init()
        }

        // Render content
        SplashContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}