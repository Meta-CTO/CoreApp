package com.metacto.core.presentation.globalState.models

import com.metacto.core.resources.FileResource

sealed class LoadingType {
    data class Lottie(val anim: FileResource? = null) : LoadingType()
    data class LottieBlocking(val anim: FileResource? = null) : LoadingType()
    data object PrimaryCircular : LoadingType()
    data object PrimaryCircularBlocking : LoadingType()
    data object SecondaryCircular : LoadingType()
    data object SecondaryCircularBlocking : LoadingType()
    data object HiddenBlocking : LoadingType()
    data object NoLoading : LoadingType()
}