package com.sampleApp.app.resources

import com.metacto.core.resources.FileResource
import com.metacto.core.resources.asFileResource

val Res.file: FileResources
    get() = FileResources


object FileResources {
    val app_loading: FileResource = "app_loading.json".asFileResource()
}