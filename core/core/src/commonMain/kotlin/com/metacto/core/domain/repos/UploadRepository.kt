package com.metacto.core.domain.repos

import com.metacto.core.CoreConfigs
import com.metacto.core.domain.models.request.UpdatePreviewUrlRequest
import com.metacto.core.domain.models.request.UpdatePreviewUrlRequestData
import com.metacto.core.extensions.randomUUID
import com.metacto.kmm.network.JsonFlatter
import com.metacto.kmm.network.constants.SharedConstants
import com.metacto.kmm.network.model.image.Image
import com.metacto.kmm.network.services.HttpService
import com.metacto.kmm.network.services.convert
import com.metacto.kmm.sharedpreferences.KmmPreference
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject

class UploadRepository(
    private val coreConfigs: CoreConfigs,
    private val httpService: HttpService,
    private val sharedPreference: KmmPreference
) {
    @Throws(Throwable::class)
    suspend fun uploadImage(bytes: ByteArray, fileName: String = randomUUID()): Image {
        return uploadMedia(
            bytes = bytes,
            fileName = "$fileName.jpg"
        )
    }

    @Throws(Throwable::class)
    suspend fun uploadVideo(
        bytes: ByteArray,
        fileName: String = randomUUID(),
        previewUrl: String? = null
    ): Image {
        val video = uploadMedia(
            bytes = bytes,
            fileName = "$fileName.mp4"
        )

        if (previewUrl != null && video.id != null) {
            updateVideoPreviewUrl(video.id!!, previewUrl)
        }

        return video
    }

    @Throws(Throwable::class)
    private suspend fun uploadMedia(bytes: ByteArray, fileName: String): Image {
        val token = sharedPreference.getSecureString(SharedConstants.ACCESS_TOKEN)
        val response = httpService.httpClient.submitFormWithBinaryData(
            url = "${coreConfigs.baseUrl}/upload",
            formData = formData {
                append(
                    "files",
                    bytes,
                    Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=$fileName")
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                )
            }
        )

        val responseContent = ((response.body() as JsonArray)[0].jsonObject)
        return JsonFlatter.flat<Image>(responseContent).convert()
    }

    @Throws(Throwable::class)
    suspend fun updateVideoPreviewUrl(id: Int, previewUrl: String) {
        httpService.put<Unit> {
            endpoint("/custom-uploader/{id}")
            path("id", id.toString())
            body(UpdatePreviewUrlRequest(UpdatePreviewUrlRequestData(previewUrl)))
        }
    }

    suspend fun downloadBytes(
        url: String,
        headers: Map<String, List<String>> = emptyMap()
    ): ByteArray? {
        return try {
            val response: HttpResponse = httpService.httpClient.get(url) {
                headers.forEach { (key, value) -> header(key, value) }
            }
            response.body()
        } catch (e: Exception) {
            null
        }
    }
}