package com.sampleApp.app.resources

import com.metacto.core.resources.IFileResource

val Res.file: FileResources
    get() = FileResources

fun String.asFileResource(): IFileResource = FileResource(path = "files/$this")

object FileResources {
    val app_loading: IFileResource = "app_loading.json".asFileResource()
}