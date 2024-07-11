package com.metacto.core.domain.repos

import com.metacto.core.CoreEnvironment
import com.metacto.strapikmm.datasource.network.services.strapi.StrapiService
import com.metacto.strapikmm.errorhandling.SerializableNetworkError
import com.metacto.strapikmm.sharedpreference.KmmPreference
import kotlin.reflect.KClass

expect open class RepositoriesFactory<T : SerializableNetworkError> {
    val sharedPreference: KmmPreference
    val strapiService: StrapiService
    val environment: CoreEnvironment
    val appStorageName: String
    val shouldShowActualErrorMessages: Boolean
    val errorClass: KClass<T>
}