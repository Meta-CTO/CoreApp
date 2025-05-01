package com.sampleApp.app.resources

import com.metacto.core.ui.resources.IFileResource

val Res.file: FileResources
    get() = FileResources

fun String.asFileResource(): IFileResource = FileResource(path = "files/$this")

object FileResources {
    val app_loading = "app_loading.json".asFileResource()
    val intro_video = "intro_video.mp4".asFileResource()
}