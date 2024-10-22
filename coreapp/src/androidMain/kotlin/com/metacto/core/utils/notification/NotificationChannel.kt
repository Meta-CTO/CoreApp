package com.metacto.core.utils.notification

import com.metacto.core.utils.CommonParcelable
import com.metacto.core.utils.CommonParcelize

@CommonParcelize
data class NotificationChannel(
    val id: String,
    val name: String,
) : CommonParcelable