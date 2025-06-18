package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
import com.metacto.core.domain.models.request.UpdatePreviewUrlRequest
import com.metacto.core.domain.models.request.UpdatePreviewUrlRequestData
import com.metacto.core.utils.extensions.randomUUID
import com.metacto.strapikmm.constants.SharedConstants
import com.metacto.strapikmm.datasource.network.services.strapi.JsonFlatter
import com.metacto.strapikmm.datasource.network.services.strapi.StrapiService
import com.metacto.strapikmm.datasource.network.services.strapi.convert
import com.metacto.strapikmm.model.image.Image
import com.metacto.strapikmm.sharedpreference.KmmPreference
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
    private val appEnvironment: CoreEnvironment,
    private val uploadService: StrapiService,
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
        val response = uploadService.httpClient.submitFormWithBinaryData(
            url = "${appEnvironment.baseUrl}/upload",
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
        uploadService.put<Unit> {
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
            val response: HttpResponse = uploadService.httpClient.get(url) {
                headers.forEach { (key, value) -> header(key, value) }
            }
            response.body()
        } catch (e: Exception) {
            null
        }
    }
}