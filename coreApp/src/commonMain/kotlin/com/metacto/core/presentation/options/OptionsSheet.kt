package com.metacto.core.presentation.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.SIDE_EFFECTS_KEY
import com.metacto.core.presentation.options.components.OptionsContent
import com.metacto.core.presentation.options.models.OptionUIModel

class OptionsSheet(
    private val options: List<OptionUIModel>
) : BaseScreen<OptionsViewModel>() {

    @Composable
    override fun Content() {
        // Create main objects
        val viewModel = rememberViewModel<OptionsViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.init(
                options = options
            )
        }

        // Render content
        OptionsContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}