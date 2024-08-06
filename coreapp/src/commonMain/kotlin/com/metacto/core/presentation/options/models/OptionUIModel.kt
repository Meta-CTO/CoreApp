package com.metacto.core.presentation.options.models

import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
data class OptionUIModel(
    val id: Int = 0,
    val title: String,
    val icon: CommonImageResource? = null,
    val color: ULong? = null,
    val hasArrow: Boolean = false
) : CommonParcelable
