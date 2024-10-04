package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
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
    suspend fun uploadImage(bytes: ByteArray, imageName: String = randomUUID()): Image {
        val token = sharedPreference.getSecureString(SharedConstants.ACCESS_TOKEN)
        val response = uploadService.httpClient.submitFormWithBinaryData(
            url = "${appEnvironment.baseUrl}/upload",
            formData = formData {
                append(
                    "files",
                    bytes,
                    Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=$imageName.jpg")
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                )
            }
        )

        val responseContent = ((response.body() as JsonArray)[0].jsonObject)
        return JsonFlatter.flat<Image>(responseContent).convert()
    }
}