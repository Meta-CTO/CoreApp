package com.metacto.core.ui.resources

internal val Res.file: FileResources
    get() = FileResources

fun String.asFileResource(): IFileResource = FileResource(path = "files/$this")

object FileResources {
    val loading_indicator_anim: IFileResource = "loading_indicator_anim.json".asFileResource()
}