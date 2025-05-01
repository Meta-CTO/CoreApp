package com.metacto.core.extensions

import com.metacto.kmm.network.errorhandling.AppException
import com.metacto.kmm.network.errorhandling.NetworkMapperConstants

fun Throwable.isInternetConnectionError(): Boolean {
    return this is AppException && this.getErrorCode() == NetworkMapperConstants.NO_INTERNET_CONNECTION
}

expect fun AppException.getHttpErrorCode(): Int?
expect fun AppException.getErrorCode(): Int?
expect fun AppException.getErrorMessage(): String?
expect fun AppException.getErrorBody(): String?