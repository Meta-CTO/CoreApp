package com.sampleApp.app.presentation.home

import androidx.compose.runtime.Composable
import com.metacto.core.presentation.base.BaseTabScreen
import com.metacto.core.presentation.base.rememberViewModel
import com.sampleApp.app.presentation.home.HomeContract.Event
import com.sampleApp.app.presentation.home.components.HomeContent

internal object HomeTab : BaseTabScreen<HomeViewModel>() {
    private var viewModel: HomeViewModel? = null

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<HomeViewModel>()
        this.viewModel = viewModel

        // Render content
        HomeContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }

    override fun onDisplayed() {
        super.onDisplayed()
        viewModel?.setEvent(Event.Init)
    }
}
