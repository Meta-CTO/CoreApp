package com.metacto.core.utils.extensions

import com.metacto.core.presentation.base.getErrorCode
import com.metacto.strapikmm.errorhandling.AppException
import com.metacto.strapikmm.errorhandling.NetworkMapperConstants

fun Throwable.isInternetConnectionError(): Boolean {
    return this is AppException && this.getErrorCode() == NetworkMapperConstants.NO_INTERNET_CONNECTION
}