package com.metacto.core.presentation.options

import com.metacto.core.presentation.base.BaseSheet
import com.metacto.core.presentation.options.models.OptionUIModel

expect class OptionsSheet(
    options: List<OptionUIModel>
) : BaseSheet<OptionsViewModel> {

    val options: List<OptionUIModel>
}