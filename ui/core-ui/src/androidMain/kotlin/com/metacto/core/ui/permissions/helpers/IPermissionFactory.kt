package com.metacto.core.ui.permissions.helpers

import com.metacto.core.ui.permissions.enums.Permission

interface IPermissionFactory {
    fun getPermissions(permission: Permission): List<String>
}