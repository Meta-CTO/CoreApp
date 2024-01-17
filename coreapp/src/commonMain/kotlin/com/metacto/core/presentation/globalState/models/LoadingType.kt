package com.metacto.core.presentation.globalState.models

import dev.icerock.moko.resources.AssetResource

sealed class LoadingType {
    data class Lottie(val anim: AssetResource? = null) : LoadingType()
    data class LottieBlocking(val anim: AssetResource? = null) : LoadingType()
    data object PrimaryCircular : LoadingType()
    data object PrimaryCircularBlocking : LoadingType()
    data object SecondaryCircular : LoadingType()
    data object SecondaryCircularBlocking : LoadingType()
    data object HiddenBlocking : LoadingType()
    data object NoLoading : LoadingType()
}