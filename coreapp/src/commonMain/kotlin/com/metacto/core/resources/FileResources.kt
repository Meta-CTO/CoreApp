package com.metacto.core.resources

import com.metacto.coreApp.resources.Res

val Res.file: FileResources
    get() = FileResources

fun String.asFileResource(): IFileResource = FileResource(path = "files/$this")

object FileResources {
    val loading_indicator_anim: IFileResource = "loading_indicator_anim.json".asFileResource()
}