package com.metacto.core.extensions

import com.metacto.kmm.network.errorhandling.AppException

actual fun AppException.getErrorCode(): Int? {
    return this.error.userInfo["errorCode"] as Int?
}

actual fun AppException.getErrorMessage(): String? {
    return this.error.localizedDescription
}

actual fun AppException.getHttpErrorCode(): Int? {
    return this.error.userInfo["httpErrorCode"] as Int?
}

actual fun AppException.getErrorBody(): String? {
    return this.error.userInfo["errorBody"] as String?
}