package com.metacto.core.extensions

import com.metacto.kmm.network.errorhandling.AppException

actual fun AppException.getErrorCode(): Int? {
    return this.errorCode
}

actual fun AppException.getErrorMessage(): String? {
    return this.errorMessage
}

actual fun AppException.getErrorBody(): String? {
    return this.errorBody
}

actual fun AppException.getHttpErrorCode(): Int? {
    return this.httpErrorCode
}