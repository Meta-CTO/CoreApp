package com.metacto.core.notifications

import com.metacto.kmm.core.CommonParcelable
import com.metacto.kmm.core.CommonParcelize

@CommonParcelize
data class NotificationChannel(
    val id: String,
    val name: String,
) : CommonParcelable