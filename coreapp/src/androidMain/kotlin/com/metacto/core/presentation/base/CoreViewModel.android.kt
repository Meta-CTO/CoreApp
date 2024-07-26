package com.metacto.core.presentation.base

import com.metacto.strapikmm.errorhandling.AppException

actual fun AppException.getErrorCode(): Int? {
    return this.errorCode
}

actual fun AppException.getErrorMessage(): String? {
    return this.errorMessage
}

actual fun AppException.getHttpErrorCode(): Int? {
    return this.errorCode
}