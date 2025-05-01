package com.metacto.core.ui.components.options

import androidx.compose.runtime.Composable
import com.metacto.core.ui.base.BaseSheet
import com.metacto.core.ui.base.getViewModel
import com.metacto.core.ui.components.options.OptionsContract.Event
import com.metacto.core.ui.components.options.components.OptionsContent
import com.metacto.core.ui.components.options.models.OptionUIModel

actual class OptionsSheet actual constructor(
    actual val options: List<OptionUIModel>
) : BaseSheet<OptionsViewModel>() {

    private val viewModel = getViewModel<OptionsViewModel>()

    init {
        // Init view model
        viewModel.setEvent(
            Event.Init(
                options = options
            )
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