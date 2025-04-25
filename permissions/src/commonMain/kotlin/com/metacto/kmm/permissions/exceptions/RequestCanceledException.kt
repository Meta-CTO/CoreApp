package com.metacto.core.permissions.exceptions

import com.metacto.kmm.permissions.enums.Permission

class RequestCanceledException(
    val permission: Permission,
    message: String? = null
) : Exception(message)
