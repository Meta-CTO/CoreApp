package com.metacto.core.presentation.components.tabsLayout

import com.metacto.core.presentation.models.ImageUIModel
import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
class TabItemModel(
    val title: String,
    val activeIcon: ImageUIModel? = null,
    val inactiveIcon: ImageUIModel? = null,
) : CommonParcelable