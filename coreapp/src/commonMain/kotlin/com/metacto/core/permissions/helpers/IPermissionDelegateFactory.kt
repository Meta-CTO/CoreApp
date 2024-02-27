package com.metacto.core.permissions.helpers

import com.metacto.core.permissions.enums.Permission

interface IPermissionDelegateFactory {
    fun getDelegate(permission: Permission): PermissionDelegate
}