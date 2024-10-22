package com.metacto.core.presentation.options.models

import com.metacto.core.utils.CommonSerializable
import org.jetbrains.compose.resources.DrawableResource

data class OptionUIModel(
    val id: Int = 0,
    val title: String,
    val icon: DrawableResource? = null,
    val color: String? = null,
    val hasArrow: Boolean = false
) : CommonSerializable
