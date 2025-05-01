package com.metacto.core.ui.components.options

import com.metacto.core.ui.base.BaseSheet
import com.metacto.core.ui.components.options.models.OptionUIModel

expect class OptionsSheet(
    options: List<OptionUIModel>
) : BaseSheet<OptionsViewModel> {

    val options: List<OptionUIModel>
}