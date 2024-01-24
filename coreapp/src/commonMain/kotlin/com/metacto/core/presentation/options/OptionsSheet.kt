package com.metacto.core.presentation.options

import androidx.compose.runtime.Composable
import com.metacto.core.presentation.base.BaseScreen
import com.metacto.core.presentation.base.getViewModel
import com.metacto.core.presentation.options.components.OptionsContent
import com.metacto.core.presentation.options.models.OptionUIModel

class OptionsSheet(
    options: List<OptionUIModel>
) : BaseScreen<OptionsViewModel>() {

    private val viewModel = getViewModel<OptionsViewModel>()

    init {
        // Init view model
        viewModel.init(
            options = options
        )
    }

    @Composable
    override fun Content() {
        // Render content
        OptionsContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}