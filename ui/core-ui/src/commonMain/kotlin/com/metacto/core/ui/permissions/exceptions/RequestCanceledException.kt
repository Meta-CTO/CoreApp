package com.metacto.core.ui.permissions.exceptions

import com.metacto.core.ui.permissions.enums.Permission

class RequestCanceledException(
    val permission: Permission,
    message: String? = null
) : Exception(message)
