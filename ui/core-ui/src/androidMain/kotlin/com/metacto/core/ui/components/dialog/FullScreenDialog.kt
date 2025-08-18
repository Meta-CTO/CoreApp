package com.metacto.core.ui.components.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

private const val FULLSCREEN_DIALOG_TAG = "fullscreen-dialog"

class FullScreenComposeDialog(
    val backgroundColor: Color,
    val content: @Composable BoxScope.() -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(
            requireContext(),
            android.R.style.Theme_Black_NoTitleBar_Fullscreen
        )

        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            WindowInsetsControllerCompat(this, decorView).apply {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        dialog.setContentView(ComposeView(requireContext()).apply {
            setContent {
                Box(
                    content = content,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                )
            }
        })

        return dialog
    }
}

fun AppCompatActivity.showFullScreenDialog(
    backgroundColor: Color = Color.Black,
    content: @Composable BoxScope.() -> Unit
) {
    FullScreenComposeDialog(
        backgroundColor = backgroundColor,
        content = content
    ).show(supportFragmentManager, FULLSCREEN_DIALOG_TAG)
}

fun FragmentActivity.dismissFullScreenDialog() {
    supportFragmentManager
        .findFragmentByTag(FULLSCREEN_DIALOG_TAG)
        ?.let { (it as? DialogFragment)?.dismiss() }
}