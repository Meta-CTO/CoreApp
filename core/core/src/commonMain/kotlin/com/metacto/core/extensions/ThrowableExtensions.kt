package com.metacto.core.extensions

import com.metacto.kmm.network.errorhandling.AppException
import com.metacto.kmm.network.errorhandling.NetworkMapperConstants

expect fun AppException.getHttpErrorCode(): Int?
expect fun AppException.getErrorCode(): Int?
expect fun AppException.getErrorMessage(): String?
expect fun AppException.getErrorBody(): String?

private val INTERNET_ERROR_RANGE = -1000 downTo -1005
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

fun Throwable.isConnectionLostError(): Boolean {
    return this.message == NETWORK_CONNECTION_LOST
}