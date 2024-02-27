package com.metacto.core.permissions.helpers.exceptions

import com.metacto.core.permissions.helpers.enums.Permission

class RequestCanceledException(
    val permission: Permission,
    message: String? = null
) : Exception(message)
