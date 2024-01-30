package com.metacto.core.presentation.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.base.getViewModel
import com.metacto.core.presentation.options.components.OptionsContent
import com.metacto.core.presentation.options.models.OptionUIModel
import com.metacto.core.presentation.options.OptionsContract.Event

actual class OptionsSheet actual constructor(
    actual val options: List<OptionUIModel>
) : BaseSheet<OptionsViewModel>() {

    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = getViewModel<OptionsViewModel>()

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