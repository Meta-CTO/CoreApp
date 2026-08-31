package com.metacto.core.domain.repos

import com.metacto.kmm.network.HttpClientConfiguration
import com.metacto.kmm.network.KtorClientFactory
import com.metacto.kmm.network.ServerErrorMapper
import com.metacto.kmm.network.errorhandling.SerializableNetworkError
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

sealed interface ApiErrorHandling<T : Any> {

    fun buildClient(factory: KtorClientFactory, configure: HttpClientConfiguration): HttpClient

    data class Declared<T : SerializableNetworkError>(
        val errorClass: KClass<T>
    ) : ApiErrorHandling<T> {
        override fun buildClient(factory: KtorClientFactory, configure: HttpClientConfiguration) =
            factory.build(errorClass = errorClass, configure = configure)
    }

    data class Mapped<T : Any>(
        val serializer: KSerializer<T>,
        val toException: ServerErrorMapper<T>
    ) : ApiErrorHandling<T> {
        override fun buildClient(factory: KtorClientFactory, configure: HttpClientConfiguration) =
            factory.build(
                errorSerializer = serializer,
                toException = toException,
                configure = configure
            )
    }
}
