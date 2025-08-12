package com.metacto.core.ui.components.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.ui.base.CoreSheet
import com.metacto.core.ui.base.SIDE_EFFECTS_KEY
import com.metacto.core.ui.base.rememberViewModel
import com.metacto.core.ui.components.options.components.OptionsContent
import com.metacto.core.ui.components.options.models.OptionUIModel
import com.metacto.core.ui.components.options.OptionsContract.Event

actual class OptionsSheet actual constructor(
    actual val options: List<OptionUIModel>
) : CoreSheet<OptionsViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<OptionsViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                Event.Init(
                    options = options
                )
            )
        }

        // Render content
        OptionsContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}