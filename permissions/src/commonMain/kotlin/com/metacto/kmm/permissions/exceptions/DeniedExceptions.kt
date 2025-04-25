package com.metacto.kmm.permissions.exceptions

import com.metacto.kmm.permissions.enums.Permission

open class DeniedException(
    val permission: Permission,
    message: String? = null
) : Exception(message)

class DeniedAlwaysException(
    permission: Permission,
    message: String? = null
) : DeniedException(permission, message)
