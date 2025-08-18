package com.metacto.catalogapp.presentation.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.lottie.LottieContract.Event
import com.metacto.catalogapp.presentation.lottie.components.LottieContent
import com.metacto.catalogapp.presentation.base.BaseScreen
import com.metacto.core.ui.base.rememberViewModel

internal class LottieScreen : BaseScreen<LottieViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<LottieViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            if (!viewModel.viewState.value.isInitialized) {
                viewModel.setEvent(Event.Init)
            }
        }

        // Render content
        LottieContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
