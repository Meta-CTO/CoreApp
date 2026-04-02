package com.metacto.core.permissions.helpers

import com.metacto.core.permissions.enums.Permission

interface IPermissionFactory {
    fun getPermissions(permission: Permission): List<String>
}