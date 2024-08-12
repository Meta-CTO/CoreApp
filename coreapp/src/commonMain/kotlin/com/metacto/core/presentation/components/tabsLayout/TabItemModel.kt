package com.metacto.core.presentation.components.tabsLayout

import com.metacto.core.utils.CommonImageResource
import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
class TabItemModel(
    val title: String,
    val activeIcon: CommonImageResource? = null,
    val inactiveIcon: CommonImageResource? = null,
    val activeRemoteIcon: String? = null,
    val inactiveRemoteIcon: String? = null
) : CommonParcelable