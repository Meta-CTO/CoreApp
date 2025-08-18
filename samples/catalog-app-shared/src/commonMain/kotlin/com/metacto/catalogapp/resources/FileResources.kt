package com.metacto.catalogapp.resources

import com.metacto.core.ui.resources.IFileResource

internal val Res.file: FileResources
    get() = FileResources

internal fun String.asFileResource(): IFileResource = FileResource(path = "files/$this")

internal object FileResources {
    val app_loading = "app_loading.json".asFileResource()
    val intro_video = "intro_video.mp4".asFileResource()
    val people_lottie_anim = "people_lottie_anim.json".asFileResource()
}