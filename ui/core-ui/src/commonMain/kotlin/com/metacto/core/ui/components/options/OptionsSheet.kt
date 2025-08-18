package com.metacto.core.ui.components.options

import com.metacto.core.ui.base.CoreSheet
import com.metacto.core.ui.components.options.models.OptionUIModel

expect class OptionsSheet(
    options: List<OptionUIModel>
) : CoreSheet<OptionsViewModel> {

    val options: List<OptionUIModel>
}