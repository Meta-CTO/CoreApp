package com.sampleApp.app.presentation.profile

import androidx.compose.runtime.Composable
import com.metacto.core.presentation.base.BaseTabScreen
import com.metacto.core.presentation.base.rememberViewModel
import com.sampleApp.app.presentation.profile.ProfileContract.Event
import com.sampleApp.app.presentation.profile.components.ProfileContent

internal object ProfileTab : BaseTabScreen<ProfileViewModel>() {
    private var viewModel: ProfileViewModel? = null

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<ProfileViewModel>()
        this.viewModel = viewModel

        // Render content
        ProfileContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }

    override fun onDisplayed() {
        super.onDisplayed()
        viewModel?.setEvent(Event.Init)
    }
}
