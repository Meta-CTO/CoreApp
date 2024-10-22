package com.metacto.core.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.metacto.coreApp.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

val Res.file: FileResources
    get() = FileResources

data class FileResource(
    val path: String
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FileResource.readTextAsState(): State<String?> {
    return produceState<String?>(null, this) {
        value = Res.readBytes(path).decodeToString()
    }
}

fun String.asFileResource() = FileResource(path = "files/$this")

object FileResources {
    val loading_indicator_anim: FileResource = "loading_indicator_anim.json".asFileResource()
}