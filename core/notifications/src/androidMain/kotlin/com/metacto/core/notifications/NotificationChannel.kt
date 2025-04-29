package com.metacto.core.notifications

import com.metacto.core.CommonParcelable
import com.metacto.core.CommonParcelize

@CommonParcelize
data class NotificationChannel(
    val id: String,
    val name: String,
) : CommonParcelable