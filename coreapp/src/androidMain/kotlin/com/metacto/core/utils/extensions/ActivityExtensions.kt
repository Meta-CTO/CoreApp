package com.metacto.core.utils.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(@IdRes containerId: Int, fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(containerId, fragment)
        .commitAllowingStateLoss()
}

fun FragmentActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .remove(fragment)
        .commitAllowingStateLoss()
}