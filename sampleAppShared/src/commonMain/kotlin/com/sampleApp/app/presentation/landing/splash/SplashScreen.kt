package com.sampleApp.app.presentation.landing.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.rememberViewModel
import com.sampleApp.app.presentation.landing.splash.SplashContract.Event
import com.sampleApp.app.presentation.landing.splash.components.SplashContent
import dev.icerock.moko.permissions.compose.BindEffect

internal class SplashScreen(
    private val isWelcome: Boolean = false
) : BaseScreen<SplashViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<SplashViewModel>()

        // Notify screen appeared
        viewModel.setEvent(Event.ScreenAppeared)

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.init(
                isWelcome = isWelcome
            )
        }

        // Binds the permissions controller to the LocalLifecycleOwner lifecycle.
        BindEffect(viewModel.permissionsController)

        // Render content
        SplashContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}