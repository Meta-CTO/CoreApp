package com.metacto.core.ui.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
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

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.setPortraitOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.setLandscapeOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

fun Activity.setUnspecifiedOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}