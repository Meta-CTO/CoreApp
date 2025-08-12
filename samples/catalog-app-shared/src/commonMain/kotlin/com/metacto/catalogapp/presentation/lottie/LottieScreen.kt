package com.metacto.catalogapp.presentation.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.catalogapp.presentation.lottie.LottieContract.Event
import com.metacto.catalogapp.presentation.lottie.components.LottieContent
import com.metacto.core.ui.base.CoreScreen
import com.metacto.core.ui.base.rememberViewModel

internal class LottieScreen : CoreScreen<LottieViewModel>() {
    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<LottieViewModel>()

        // Init view model
        LaunchedEffect(Unit) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        LottieContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}
