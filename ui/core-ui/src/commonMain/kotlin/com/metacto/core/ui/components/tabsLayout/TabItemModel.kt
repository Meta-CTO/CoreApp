package com.metacto.core.ui.components.tabsLayout

import com.metacto.core.CommonParcelable
import com.metacto.core.CommonParcelize
import com.metacto.core.ui.models.ImageUIModel

@CommonParcelize
class TabItemModel(
    val title: String,
    val activeIcon: ImageUIModel? = null,
    val inactiveIcon: ImageUIModel? = null,
    val activeColor: ULong? = null,
    val inactiveColor: ULong? = null,
) : CommonParcelable