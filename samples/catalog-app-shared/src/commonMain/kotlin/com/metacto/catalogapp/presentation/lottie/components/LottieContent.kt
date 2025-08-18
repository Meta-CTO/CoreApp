package com.metacto.catalogapp.presentation.lottie.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.metacto.core.ui.navigation.NavManager
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import org.koin.compose.koinInject
import com.metacto.catalogapp.presentation.lottie.LottieContract.State
import com.metacto.catalogapp.presentation.lottie.LottieContract.Event
import com.metacto.catalogapp.resources.Res
import com.metacto.catalogapp.resources.file
import com.metacto.core.ui.extensions.rememberLottieComposition
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState

@Composable
internal fun LottieContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()

    // Prepare lottie composition
    val composition by rememberLottieComposition(Res.file.people_lottie_anim)
    val progress = animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Container column
    AppScreenColumn(
        title = "Lottie",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress.value },
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
