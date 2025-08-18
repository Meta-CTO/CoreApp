package com.metacto.core.ui.permissions.helpers

import com.metacto.core.ui.permissions.enums.Permission

interface IPermissionDelegateFactory {
    fun getDelegate(permission: Permission): PermissionDelegate
}