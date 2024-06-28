package com.metacto.core.utils.extensions

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(
            key,
            T::class.java
        )
    } else {
        getSerializableExtra(key) as? T?
    }
}

inline fun <reified T : Parcelable> Intent.getParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(
            key,
            T::class.java
        )
    } else {
        getParcelableExtra(key) as? T?
    }
}

fun Intent.addExtras(extras: Map<String, Any>) {
    extras.forEach { (key, value) ->
        when (value) {
            is String -> putExtra(key, value)
            is Int -> putExtra(key, value)
            is Long -> putExtra(key, value)
            is Float -> putExtra(key, value)
            is Double -> putExtra(key, value)
            is Boolean -> putExtra(key, value)
            is Short -> putExtra(key, value)
            is Byte -> putExtra(key, value)
            is Char -> putExtra(key, value)
            is CharSequence -> putExtra(key, value)
            is Serializable -> putExtra(key, value)
            is Parcelable -> putExtra(key, value)
            else -> {}
        }
    }
}