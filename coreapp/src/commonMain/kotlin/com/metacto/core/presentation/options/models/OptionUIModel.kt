package com.metacto.core.presentation.options.models

import com.metacto.core.utils.CommonParcelize
import com.metacto.core.utils.CommonSerializable
import dev.icerock.moko.resources.ImageResource

data class OptionUIModel(
    val id: Int = 0,
    val title: String,
    val icon: ImageResource? = null,
    val color: ULong? = null,
    val hasArrow: Boolean = false
) : CommonSerializable
