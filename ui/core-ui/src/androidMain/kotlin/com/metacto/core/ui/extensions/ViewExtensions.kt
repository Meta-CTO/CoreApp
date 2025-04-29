package com.metacto.core.ui.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun View.hideKeyboard() {
    val manager = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    manager?.hideSoftInputFromWindow(windowToken, 0)
}