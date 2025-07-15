package com.metacto.core.utils.extensions

import com.metacto.core.presentation.base.getErrorCode
import com.metacto.strapikmm.errorhandling.AppException
import com.metacto.strapikmm.errorhandling.NetworkMapperConstants

val INTERNET_ERROR_RANGE = -1000 downTo -1005
const val NETWORK_CONNECTION_LOST = "network connection was lost"

fun Throwable.isInternetConnectionError(): Boolean {
    return this is AppException && this.getErrorCode() == NetworkMapperConstants.NO_INTERNET_CONNECTION
}

fun Throwable.isInternetInterruptedError(): Boolean {
    return this is AppException && this.getErrorCode() in INTERNET_ERROR_RANGE
}

fun Throwable.isNetworkConnectionLostError(): Boolean {
    return this is AppException && this.message == NETWORK_CONNECTION_LOST
}