package com.metacto.catalogapp.presentation.mediaManager.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.metacto.catalogapp.presentation.components.containers.AppScreenColumn
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerContract.Event
import com.metacto.catalogapp.presentation.mediaManager.MediaManagerContract.State
import com.metacto.catalogapp.presentation.theme.spacings
import com.metacto.core.ui.components.images.AppImage
import com.metacto.core.ui.media.IMediaManager
import com.metacto.core.ui.models.ImageUIModel
import com.metacto.core.ui.navigation.NavManager
import org.koin.compose.koinInject

@Composable
internal fun MediaManagerContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Di
    val navManager = koinInject<NavManager>()
    val mediaManager = koinInject<IMediaManager>()

    // State
    var image by remember { mutableStateOf(ImageUIModel()) }

    LaunchedEffect(Unit) {
        val videoPreview =
            mediaManager.getVideoPreview("https://www.w3schools.com/html/mov_bbb.mp4")
        image = image.copy(bytes = videoPreview)
    }
    // Container column
    AppScreenColumn(
        title = "Media Manager",
        isScrollable = true,
        showToolbar = true,
        showBack = true,
        onBackClick = {
            navManager.goBack()
        },
    ) {

        AppImage(
            image = image,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(spacings.spacing300)

        )

    }
}
