package com.metacto.core.permissions.helpers.exceptions

import com.metacto.core.permissions.helpers.enums.Permission

open class DeniedException(
    val permission: Permission,
    message: String? = null
) : Exception(message)

class DeniedAlwaysException(
    permission: Permission,
    message: String? = null
) : DeniedException(permission, message)
