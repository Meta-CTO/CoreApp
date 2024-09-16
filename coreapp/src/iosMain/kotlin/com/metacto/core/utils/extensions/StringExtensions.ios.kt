package com.metacto.core.utils.extensions

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun String.formatNatively(vararg args: Any): String {
    return NSString.stringWithFormat(this, args.map { it })
}