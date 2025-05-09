package com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderContract.Event
import com.metacto.catalogapp.presentation.imagePreloader.imagepreloader.ImagePreloaderContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.images.AppImage
import com.metacto.core.ui.imagePreloader.IPreloader
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun ImagePreloaderContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val imagePreloader = koinInject<IPreloader>()

    // launch effect image preloader
    LaunchedEffect(Unit) {
        imagePreloader.preloadImages("https://gratisography.com/wp-content/uploads/2025/01/gratisography-dog-vacation-800x525.jpg")
    }

    // Container column
    AppScreenColumn(
        title = "Image Preloader",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {

        AppImage(
            url = "https://gratisography.com/wp-content/uploads/2025/01/gratisography-dog-vacation-800x525.jpg",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(spacings.spacing300)
        )

    }
}
