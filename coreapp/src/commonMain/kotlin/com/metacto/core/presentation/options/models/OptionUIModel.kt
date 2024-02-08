package com.metacto.core.presentation.options.models

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize
import dev.icerock.moko.resources.ImageResource

@CommonParcelize
data class OptionUIModel(
    val id: Int = 0,
    val title: String,
    val icon: ImageResource? = null,
    val color: ULong? = null,
    val hasArrow: Boolean = false
) : CommonParcelable
