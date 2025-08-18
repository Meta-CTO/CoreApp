package com.metacto.core.ui.components.options.models

import com.metacto.kmm.core.CommonSerializable
import org.jetbrains.compose.resources.DrawableResource

data class OptionUIModel(
    val id: Int = 0,
    val title: String,
    val icon: DrawableResource? = null,
    val color: String? = null,
    val hasArrow: Boolean = false
) : CommonSerializable
